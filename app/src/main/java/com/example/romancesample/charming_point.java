package com.example.romancesample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.UserApi;
import com.example.romancesample.model.PhotoResponse;

import java.util.List;

public class charming_point extends AppCompatActivity {

    Button galleryBtn;
    LinearLayout photoContainer;
    SelectionData selectionData;
    int userId = 1; // 로그인된 사용자 ID

    private final ActivityResultLauncher<Intent> pickImagesLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    photoContainer.removeAllViews(); // 기존 사진 삭제
                    Intent data = result.getData();
                    if (data.getClipData() != null) {
                        int count = Math.min(data.getClipData().getItemCount(), 3); // 최대 3장
                        for (int i = 0; i < count; i++) {
                            Uri uri = data.getClipData().getItemAt(i).getUri();
                            addPhotoToContainer(uri);
                        }
                    } else if (data.getData() != null) {
                        addPhotoToContainer(data.getData());
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charming_point);

        selectionData = (SelectionData) getIntent().getSerializableExtra("selectionData");

        galleryBtn = findViewById(R.id.button12);
        photoContainer = findViewById(R.id.photoContainer);

        // CameraActivity에서 전달된 얼굴 사진 Uri 표시
        Intent intent = getIntent();
        String leftUriStr = intent.getStringExtra("leftUri");
        String rightUriStr = intent.getStringExtra("rightUri");
        String frontUriStr = intent.getStringExtra("frontUri");

        if (leftUriStr != null) addPhotoToContainer(Uri.parse(leftUriStr));
        if (rightUriStr != null) addPhotoToContainer(Uri.parse(rightUriStr));
        if (frontUriStr != null) addPhotoToContainer(Uri.parse(frontUriStr));

        // 갤러리 선택
        galleryBtn.setOnClickListener(v -> {
            Intent intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
            intentGallery.setType("image/*");
            intentGallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            pickImagesLauncher.launch(intentGallery);
        });

        // 서버 사진 불러오기
        fetchUserPhotos();

        // 다음 화면 이동
        findViewById(R.id.next5).setOnClickListener(v -> {
            Intent nextIntent = new Intent(charming_point.this, choose_clone.class);
            nextIntent.putExtra("selectionData", selectionData);
            startActivity(nextIntent);
        });
    }

    // 사진 추가
    private void addPhotoToContainer(Uri uri) {
        ImageView img = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 250);
        params.setMargins(4, 4, 4, 4);
        img.setLayoutParams(params);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(uri).into(img);
        photoContainer.addView(img);
    }

    // 서버 사진 불러오기
    private void fetchUserPhotos() {
        UserApi api = ApiClient.getClient().create(UserApi.class);
        api.getUserPhotos(userId).enqueue(new retrofit2.Callback<List<PhotoResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<PhotoResponse>> call, retrofit2.Response<List<PhotoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayPhotos(response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<PhotoResponse>> call, Throwable t) {
                Toast.makeText(charming_point.this, "사진 목록 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 서버에서 가져온 사진 표시
    private void displayPhotos(List<PhotoResponse> photos) {
        for (PhotoResponse photo : photos) {
            addPhotoToContainer(Uri.parse(photo.getPhotoUrl()));
        }
    }
}
