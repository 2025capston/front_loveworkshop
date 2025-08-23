package com.example.romancesample;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Day2 extends BaseActivity {

    private EditText missionEditText;
    private Button inputMissionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day2);

        // 하단 네비게이션 적용 (nav_mission이 현재 선택된 메뉴)
        setupBottomNavigation(R.id.nav_mission);

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        missionEditText = findViewById(R.id.mission_text);
        inputMissionBtn = findViewById(R.id.input_mission);

        inputMissionBtn.setOnClickListener(v -> {
            String mission = missionEditText.getText().toString().trim();
            if (!mission.isEmpty()) {
                showTodayAnswerDialog();
            }
        });
    }

    private void showTodayAnswerDialog() {
        Dialog dialog = new Dialog(Day2.this);
        dialog.setContentView(R.layout.today_answer);

        Button okNext = dialog.findViewById(R.id.ok_next);
        okNext.setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(Day2.this, d_day.class);
            startActivity(intent);
        });

        dialog.show();
    }
}
