package com.example.romancesample;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.romancesample.model.UserProfileDTO;

public class choose_sex extends AppCompatActivity {

    AppCompatButton femaleButton;
    AppCompatButton maleButton;
    UserProfileDTO profile;  // 로컬에 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sex);

        ImageView next = findViewById(R.id.next);
        femaleButton = findViewById(R.id.female);
        maleButton = findViewById(R.id.male);

        // 이전 단계에서 전달받은 DTO가 있다면 사용, 없으면 새로 생성
        profile = (UserProfileDTO) getIntent().getSerializableExtra("profile");
        if (profile == null) {
            profile = new UserProfileDTO();
        }

        femaleButton.setOnClickListener(v -> {
            profile.setSex("여성");
            femaleButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.button_selected_color)));
        });

        maleButton.setOnClickListener(v -> {
            profile.setSex("남성");
            maleButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.button_selected_color)));
        });

        next.setOnClickListener(v -> {
            if (profile.getSex() == null) {
                Toast.makeText(this, "성별을 선택해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            // 다음 Activity로 DTO 전달
            Intent intent = new Intent(choose_sex.this, choose_birth.class);
            intent.putExtra("profile", profile);
            startActivity(intent);
        });
    }
}
