

package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class wait_match extends AppCompatActivity {

    private ProgressBar progressBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wait_match);

            // ProgressBar 참조
            progressBar = findViewById(R.id.progressBar);

            // 전체 화면 레이아웃 참조
            View mainLayout = findViewById(R.id.main);

            // 화면 터치 시 처리
            mainLayout.setOnClickListener(v -> {
                // ProgressBar 숨기기
                progressBar.setVisibility(View.GONE);

                // 다음 화면으로 이동
                Intent intent = new Intent(wait_match.this, show_clones.class); // 다음 액티비티로 수정 가능
                startActivity(intent);
                finish();
            });

            // 시스템 바 여백 설정
            ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
