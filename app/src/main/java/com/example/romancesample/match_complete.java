package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class match_complete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match_complete);

        // 시스템 바 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 화면 터치 시 다음 화면으로 이동
        View mainLayout = findViewById(R.id.main);
        mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(match_complete.this, choose_place_m.class); // 다음 화면 설정
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        });
    }
}
