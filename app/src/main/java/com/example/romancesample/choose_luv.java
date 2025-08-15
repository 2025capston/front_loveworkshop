package com.example.romancesample;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class choose_luv extends AppCompatActivity {
    String selectedLuv = null;

    AppCompatButton heteroBtn, homoBtn, biBtn;
    AppCompatButton lastSelectedBtn = null; // 이전에 선택된 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_luv);

        // selectionData 받아오기

        // 버튼들 연결
        heteroBtn = findViewById(R.id.hetero);
        homoBtn = findViewById(R.id.homo);
        biBtn = findViewById(R.id.bi);

        heteroBtn.setOnClickListener(v -> {
            selectedLuv = "이성애자";
            updateTextColor(heteroBtn);
        });

        homoBtn.setOnClickListener(v -> {
            selectedLuv = "동성애자";
            updateTextColor(homoBtn);
        });

        biBtn.setOnClickListener(v -> {
            selectedLuv = "양성애자";
            updateTextColor(biBtn);
        });

        ImageView next = findViewById(R.id.next3);
        next.setOnClickListener(v -> {
            if (selectedLuv == null) {
                android.widget.Toast.makeText(this, "성지향성을 선택해주세요", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }


            Intent intent = new Intent(choose_luv.this, check_camera.class);
            startActivity(intent);
        });
    }

    private void updateTextColor(AppCompatButton selectedBtn) {
        int selectedColor = getColor(R.color.button_selected_color); // 미리 정의된 선택 색상

        // 이전 선택된 버튼이 있으면 원래 색상 복원 (선택한 것만 바꿔달라고 했으니 주석 처리 가능)
        if (lastSelectedBtn != null) {
            lastSelectedBtn.setTextColor(getResources().getColor(android.R.color.black)); // 원래 색상 (혹은 원하는 기본색)
        }

        // 현재 선택된 버튼 색상 변경
        selectedBtn.setTextColor(selectedColor);

        // 현재 버튼을 저장
        lastSelectedBtn = selectedBtn;
    }
}
