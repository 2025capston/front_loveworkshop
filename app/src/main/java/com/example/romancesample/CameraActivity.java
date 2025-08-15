package com.example.romancesample;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.*;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.FaceApiService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewView;
    private Button captureButton;
    private TextView directionText;
    private ExecutorService cameraExecutor;
    private FaceDetector detector;
    private ImageCapture imageCapture;

    private Uri frontUri;
    private Uri leftUri;
    private Uri rightUri;

    private boolean leftCaptured = false;
    private boolean rightCaptured = false;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private enum FaceStage {
        LEFT, RIGHT, FRONT
    }

    private FaceStage currentStage = FaceStage.LEFT;
    private static final int REQUEST_CAMERA_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = findViewById(R.id.previewView);
        captureButton = findViewById(R.id.captureButton);
        directionText = findViewById(R.id.directionText);

        captureButton.setEnabled(false);
        directionText.setText("왼쪽 얼굴을 보여주세요");

        captureButton.setOnClickListener(v -> takePhoto());

        cameraExecutor = Executors.newSingleThreadExecutor();

        FaceDetectorOptions options = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .enableTracking()
                .build();
        detector = FaceDetection.getClient(options);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;

                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, imageProxy -> {
                    @SuppressWarnings("UnsafeOptInUsageError")
                    Image mediaImage = imageProxy.getImage();
                    if (mediaImage != null) {
                        InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                        detector.process(image)
                                .addOnSuccessListener(faces -> {
                                    if (!faces.isEmpty()) {
                                        Face face = faces.get(0);

                                        float yaw = face.getHeadEulerAngleY();
                                        float roll = face.getHeadEulerAngleZ();

                                        switch (currentStage) {
                                            case LEFT:
                                                if (yaw > 20 && !leftCaptured) {
                                                    leftCaptured = true;
                                                    captureAuto("left");
                                                    runOnUiThread(() -> directionText.setText("오른쪽 얼굴을 보여주세요"));
                                                    handler.postDelayed(() -> currentStage = FaceStage.RIGHT, 3000);
                                                }
                                                break;
                                            case RIGHT:
                                                if (yaw < -20 && !rightCaptured) {
                                                    rightCaptured = true;
                                                    captureAuto("right");
                                                    runOnUiThread(() -> directionText.setText("정면 얼굴을 보여주세요"));
                                                    handler.postDelayed(() -> currentStage = FaceStage.FRONT, 3000);
                                                }
                                                break;
                                            case FRONT:
                                                boolean facingForward = Math.abs(yaw) < 10 && Math.abs(roll) < 10;
                                                runOnUiThread(() -> {
                                                    directionText.setText("정면 얼굴을 보여주세요");
                                                    captureButton.setEnabled(facingForward);
                                                });
                                                break;
                                        }
                                    } else {
                                        runOnUiThread(() -> captureButton.setEnabled(false));
                                    }
                                    imageProxy.close();
                                })
                                .addOnFailureListener(e -> {
                                    imageProxy.close();
                                    Log.e("MLKit", "Face detection failed", e);
                                });
                    } else {
                        imageProxy.close();
                    }
                });

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis, imageCapture);

            } catch (Exception e) {
                Log.e("CameraX", "Use case binding failed", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void captureAuto(String tag) {
        String filename = tag + "_" + System.currentTimeMillis() + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/CameraX-Faces");

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                .build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Uri savedUri = outputFileResults.getSavedUri();
                        Log.d("CameraX", tag + " face saved: " + savedUri);
                        if (tag.equals("left")) {
                            leftUri = savedUri;
                        } else if (tag.equals("right")) {
                            rightUri = savedUri;
                        }
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e("CameraX", "Auto capture failed for " + tag, exception);
                    }
                });
    }


    private void takePhoto() {
        String filename = "face_front_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/CameraX-Faces");

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                .build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        frontUri = outputFileResults.getSavedUri();
                        Log.d("CameraX", "Saved front image URI: " + frontUri);

                        // front만 서버 업로드
                        uploadFaceImageToServer(frontUri);

                        // 다음 액티비티로 left, right, front 전달
                        Intent intent = new Intent(CameraActivity.this, charming_point.class);
                        intent.putExtra("leftUri", leftUri.toString());
                        intent.putExtra("rightUri", rightUri.toString());
                        intent.putExtra("frontUri", frontUri.toString());
                        startActivity(intent);
                        finish();

                        Toast.makeText(CameraActivity.this, "정면 사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "정면 사진 저장 실패: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("CameraX", "Photo capture failed", exception);
                    }
                });
    }


    private void uploadFaceImageToServer(Uri uri) {
        try {
            File file = new File(FileUtils.getPath(this, uri));
            RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/jpeg"));
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            // userId 예시 - 실제 앱에서는 로그인 정보 등에서 받아와야 함
            RequestBody userIdBody = RequestBody.create("1", MediaType.parse("text/plain"));

            FaceApiService api = ApiClient.getClient().create(FaceApiService.class);
            Call<Void> call = api.registerFace(userIdBody, body);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("CameraX", "얼굴 등록 성공");
                    } else {
                        Log.e("CameraX", "얼굴 등록 실패: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("CameraX", "얼굴 등록 네트워크 오류", t);
                }
            });

        } catch (Exception e) {
            Log.e("CameraX", "파일 업로드 실패", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "카메라 권한이 필요합니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
        detector.close();
    }
}
