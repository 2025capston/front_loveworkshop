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

import androidx.appcompat.app.AppCompatActivity;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.UserApi;
import com.example.romancesample.model.PhotoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class charming_point extends AppCompatActivity {
    Button galleryBtn;
    ImageView imageView;
    final int GET_GALLERY_IMAGE = 200;

    SelectionData selectionData;
    LinearLayout photoContainer; // 사진 목록 보여줄 레이아웃

    int userId = 1; // 실제 앱에서는 로그인된 사용자 ID로 대체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charming_point);

        selectionData = (SelectionData) getIntent().getSerializableExtra("selectionData");

        galleryBtn = findViewById(R.id.button12);
        imageView = findViewById(R.id.imageView3);
        photoContainer = findViewById(R.id.photoContainer);

        // CameraActivity에서 전달된 얼굴 사진 Uri 표시
        Intent intent = getIntent();
        String leftUriStr = intent.getStringExtra("leftUri");
        String rightUriStr = intent.getStringExtra("rightUri");
        String frontUriStr = intent.getStringExtra("frontUri");

        if (leftUriStr != null) imageView.setImageURI(Uri.parse(leftUriStr));

        // 갤러리 선택
        galleryBtn.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(galleryIntent, GET_GALLERY_IMAGE);
        });

        // 사진 목록 불러오기
        fetchUserPhotos();

        // 다음 화면 이동
        ImageView next = findViewById(R.id.next5);
        next.setOnClickListener(v -> {
            Intent nextIntent = new Intent(charming_point.this, choose_clone.class);
            nextIntent.putExtra("selectionData", selectionData);
            startActivity(nextIntent);
        });
    }

    private void fetchUserPhotos() {
        UserApi api = ApiClient.getClient().create(UserApi.class);
        api.getUserPhotos(userId).enqueue(new Callback<List<PhotoResponse>>() {
            @Override
            public void onResponse(Call<List<PhotoResponse>> call, Response<List<PhotoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayPhotos(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PhotoResponse>> call, Throwable t) {
                Toast.makeText(charming_point.this, "사진 목록 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPhotos(List<PhotoResponse> photos) {
        photoContainer.removeAllViews();
        for (PhotoResponse photo : photos) {
            View photoItem = getLayoutInflater().inflate(R.layout.item_user_photo, null);
            ImageView img = photoItem.findViewById(R.id.itemPhoto);
            Button btnSetProfile = photoItem.findViewById(R.id.btnSetProfile);
            Button btnDelete = photoItem.findViewById(R.id.btnDeletePhoto);

            // 이미지 표시
            // 실제 앱에서는 Glide, Picasso 등 사용 가능
            img.setImageURI(Uri.parse(photo.getPhotoUrl()));

            // 대표 사진 설정
            btnSetProfile.setOnClickListener(v -> setProfilePhoto(photo.getId()));

            // 사진 삭제
            btnDelete.setOnClickListener(v -> deletePhoto(photo.getId()));

            photoContainer.addView(photoItem);
        }
    }

    private void setProfilePhoto(int photoId) {
        UserApi api = ApiClient.getClient().create(UserApi.class);
        api.setProfilePhoto(userId, photoId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(charming_point.this, "대표 사진으로 설정되었습니다", Toast.LENGTH_SHORT).show();
                    fetchUserPhotos(); // 갱신
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(charming_point.this, "대표 사진 설정 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletePhoto(int photoId) {
        UserApi api = ApiClient.getClient().create(UserApi.class);
        api.deletePhoto(userId, photoId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(charming_point.this, "사진이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    fetchUserPhotos(); // 갱신
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(charming_point.this, "사진 삭제 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageView.setImageURI(selectedImageUri);
                // 업로드 API 호출 가능
            }
        }
    }
}
