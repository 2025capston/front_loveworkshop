package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class After_dday extends BaseActivity {

    private Button okMeetHimBtn;
    private Button heIsBasBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_dday);

        // 하단 네비게이션 설정 (nav_alarm2를 현재 선택된 메뉴로 설정)
        setupBottomNavigation(R.id.nav_alarm2);

        // 시스템 바 패딩 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        okMeetHimBtn = findViewById(R.id.ok_meethim);
        heIsBasBtn = findViewById(R.id.heisbas);

        okMeetHimBtn.setOnClickListener(v -> {
            Intent intent = new Intent(After_dday.this, LastMission.class);
            startActivity(intent);
        });

        heIsBasBtn.setOnClickListener(v -> {
            Intent intent = new Intent(After_dday.this, BadPeople.class);
            startActivity(intent);
        });
    }
}
