package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.romancesample.model.UserProfileDTO;

public class choose_luv extends AppCompatActivity {
    String selectedLuv = null;
    AppCompatButton heteroBtn, homoBtn, biBtn;
    AppCompatButton lastSelectedBtn = null;

    UserProfileDTO profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_luv);

        // 이전 페이지에서 DTO 받기
        profile = getIntent().getParcelableExtra("profile");
        if (profile == null) {
            profile = new UserProfileDTO();
        }

        // 버튼 연결
        heteroBtn = findViewById(R.id.hetero);
        homoBtn = findViewById(R.id.homo);
        biBtn = findViewById(R.id.bi);

        heteroBtn.setOnClickListener(v -> {
            selectedLuv = "이성애자";
            profile.setSexualOrientation(selectedLuv);
            updateTextColor(heteroBtn);
        });

        homoBtn.setOnClickListener(v -> {
            selectedLuv = "동성애자";
            profile.setSexualOrientation(selectedLuv);
            updateTextColor(homoBtn);
        });

        biBtn.setOnClickListener(v -> {
            selectedLuv = "양성애자";
            profile.setSexualOrientation(selectedLuv);
            updateTextColor(biBtn);
        });

        ImageView next = findViewById(R.id.next3);
        next.setOnClickListener(v -> {
            if (selectedLuv == null) {
                android.widget.Toast.makeText(this, "성지향성을 선택해주세요", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }

            // 다음 페이지로 DTO 전달
            Intent intent = new Intent(choose_luv.this, check_camera.class);
            intent.putExtra("profile", profile);
            startActivity(intent);
        });
    }

    private void updateTextColor(AppCompatButton selectedBtn) {
        int selectedColor = getColor(R.color.button_selected_color);

        if (lastSelectedBtn != null) {
            lastSelectedBtn.setTextColor(getResources().getColor(android.R.color.black));
        }

        selectedBtn.setTextColor(selectedColor);
        lastSelectedBtn = selectedBtn;
    }
}
