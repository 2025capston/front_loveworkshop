package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class wait_day3 extends BaseActivity {

    TextView updatePlace, updateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_day3);

        // 하단 네비게이션 적용 (nav_promise가 현재 선택된 메뉴)
        setupBottomNavigation(R.id.nav_promise);

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // 화면 클릭 시 이동
        findViewById(R.id.main).setOnClickListener(v -> {
            Intent i = new Intent(wait_day3.this, Day2.class);
            startActivity(i);
        });
    }
}
