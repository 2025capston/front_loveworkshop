package com.example.romancesample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class loading extends AppCompatActivity {

    private static final String TAG = "loading";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // 시스템 바 패딩 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // image_uri를 받는다
        String imageUriString = getIntent().getStringExtra("image_uri");
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            sendImageToServer(imageUri);
        } else {
            Log.e(TAG, "No image URI received");
        }
    }

    // 이미지 URI를 서버로 전송
    private void sendImageToServer(Uri imageUri) {
        OkHttpClient client = new OkHttpClient();

        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            if (inputStream == null) {
                Log.e(TAG, "InputStream is null");
                return;
            }

            byte[] imageBytes = new byte[inputStream.available()];
            inputStream.read(imageBytes);
            inputStream.close();

            RequestBody fileBody = RequestBody.create(imageBytes, MediaType.parse("image/jpeg"));

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", "clone.jpg", fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url("http://172.20.14.231:5000/predict") // Flask 서버 주소
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Image upload failed: " + e.getMessage());
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        Log.d(TAG, "Response: " + responseData);

                        try {
                            JSONObject json = new JSONObject(responseData);
                            JSONArray images = json.getJSONArray("images");

                            ArrayList<String> imageList = new ArrayList<>();
                            for (int i = 0; i < images.length(); i++) {
                                imageList.add(images.getString(i));
                            }

                            Intent intent = new Intent(loading.this, show_clones.class);
                            intent.putStringArrayListExtra("image_list", imageList);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON parsing error: " + e.getMessage());
                        }
                    } else {
                        Log.e(TAG, "Server error: " + response.code());
                    }
                }
            });

        } catch (IOException e) {
            Log.e(TAG, "Failed to read image: " + e.getMessage());
        }
    }
}
