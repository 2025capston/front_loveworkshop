package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class meet_end extends AppCompatActivity {

    private Button btnYes, btnNotYet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_end);
        btnYes = findViewById(R.id.inginging);
        btnNotYet = findViewById(R.id.idontlikehim);

        // YES 버튼 → 다음 페이지 이동
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(meet_end.this, yes_or_no.class);
                startActivity(intent);
            }
        });

        // NOT YET 버튼 → 이전 페이지로 이동 (finish() 사용)
        btnNotYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // 이전 화면으로 돌아가기
            }
        });
    }
}
