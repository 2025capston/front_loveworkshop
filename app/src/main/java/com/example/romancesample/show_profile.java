package com.example.romancesample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.romancesample.api.UserApi;
import com.example.romancesample.model.UserProfileDTO;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.romancesample.api.RetrofitClient;

public class show_profile extends AppCompatActivity {

    private ImageView getfromCamera;
    private TextView takeCloneAge, takePlace, takeHeight, takeAge, takeSex;
    private EditText editCloneAge, editPlace;
    private Button button2;

    private UserApi userApi;
    private int userId = 1; // 예시, 실제 로그인 유저 ID 사용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        // --- 뷰 초기화 ---
        getfromCamera = findViewById(R.id.getfromCamera);
        takeCloneAge = findViewById(R.id.take_cloneage);
        takePlace = findViewById(R.id.take_place);
        takeHeight = findViewById(R.id.take_height);
        takeAge = findViewById(R.id.take_age);
        takeSex = findViewById(R.id.take_sex);

        editCloneAge = findViewById(R.id.edit_cloneage);
        editPlace = findViewById(R.id.edit_place);
        button2 = findViewById(R.id.button2);

        // --- Retrofit API 초기화 ---
        userApi = RetrofitClient.getUserApi();

        // --- 서버에서 프로필 불러오기 ---
        loadUserProfile(userId);

        // --- 수정/저장 버튼 처리 ---
        button2.setOnClickListener(v -> {
            String currentText = button2.getText().toString();

            if (currentText.equals("수정")) {
                editCloneAge.setText(takeCloneAge.getText().toString());
                editPlace.setText(takePlace.getText().toString());

                takeCloneAge.setVisibility(View.GONE);
                takePlace.setVisibility(View.GONE);
                editCloneAge.setVisibility(View.VISIBLE);
                editPlace.setVisibility(View.VISIBLE);

                button2.setText("저장");

            } else if (currentText.equals("저장")) {
                saveUserProfile(userId);
            }
        });
    }

    // 서버 GET 호출
    private void loadUserProfile(int userId) {
        userApi.getProfile(userId).enqueue(new Callback<UserProfileDTO>() {
            @Override
            public void onResponse(Call<UserProfileDTO> call, Response<UserProfileDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfileDTO profile = response.body();

                    takeCloneAge.setText(profile.getCloneAge());
                    takePlace.setText(profile.getPlace());
                    takeHeight.setText(profile.getHeight() + "cm");
                    takeAge.setText(profile.getAge() + "세");
                    takeSex.setText(profile.getSex());

                    if (profile.getPhotoUri() != null) {
                        Glide.with(show_profile.this)
                                .load(profile.getPhotoUri())
                                .into(getfromCamera);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfileDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // 서버 PUT 호출
    private void saveUserProfile(int userId) {
        UserProfileDTO profile = new UserProfileDTO();
        profile.setCloneAge(editCloneAge.getText().toString());
        profile.setPlace(editPlace.getText().toString());

        // 기존 화면 값 그대로 반영
        profile.setHeight(Integer.parseInt(takeHeight.getText().toString().replace("cm","")));
        profile.setAge(Integer.parseInt(takeAge.getText().toString().replace("세","")));
        profile.setSex(takeSex.getText().toString());

        userApi.updateProfile(userId, profile).enqueue(new Callback<UserProfileDTO>() {
            @Override
            public void onResponse(Call<UserProfileDTO> call, Response<UserProfileDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfileDTO updated = response.body();

                    takeCloneAge.setText(updated.getCloneAge());
                    takePlace.setText(updated.getPlace());

                    editCloneAge.setVisibility(View.GONE);
                    editPlace.setVisibility(View.GONE);
                    takeCloneAge.setVisibility(View.VISIBLE);
                    takePlace.setVisibility(View.VISIBLE);

                    button2.setText("수정");
                }
            }

            @Override
            public void onFailure(Call<UserProfileDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
