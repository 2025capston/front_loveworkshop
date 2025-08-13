package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class check_list_of_people extends BaseActivity { // BaseActivity로 변경

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_of_people);

        // 하단 네비게이션 설정
        setupBottomNavigation(R.id.nav_message);

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.main).setOnClickListener(v -> {
            Intent i = new Intent(check_list_of_people.this, match_complete.class);
            startActivity(i);
        });


        // 버튼 연결 및 클릭 리스너 설정
        Button button1 = findViewById(R.id.yesyesyes1);

        View.OnClickListener goToMatchComplete = view -> {
            Intent intent = new Intent(check_list_of_people.this, match_complete.class);
            startActivity(intent);
        };

        button1.setOnClickListener(goToMatchComplete);

    }
}
