package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Day1 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day1);

        // 하단 네비게이션 설정 (nav_mission이 현재 선택된 메뉴)
        setupBottomNavigation(R.id.nav_mission);

        // 루트 뷰
        View rootView = findViewById(R.id.main);

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 화면 클릭 시 D_Day로 이동
        rootView.setOnClickListener(v -> {
            Intent intent = new Intent(Day1.this, d_day.class);
            startActivity(intent);
        });
    }
}
