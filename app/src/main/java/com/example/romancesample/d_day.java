package com.example.romancesample;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class d_day extends BaseActivity {
    private TextView countdownText;
    private CountDownTimer countDownTimer;
    private Button checkClothBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dday);

        // 하단 네비게이션 설정 (nav_alarm2가 현재 선택된 메뉴)
        setupBottomNavigation(R.id.nav_alarm2);

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        countdownText = findViewById(R.id.countdown_text);
        checkClothBtn = findViewById(R.id.check_cloth);

        checkClothBtn.setOnClickListener(v -> showTodayClothDialog());

        startCountdown();
    }

    private void startCountdown() {
        long timeLeft = 24 * 60 * 60 * 1000; // 24시간 in milliseconds

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / 1000) / 3600;
                long minutes = ((millisUntilFinished / 1000) % 3600) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                String timeFormatted = String.format(Locale.getDefault(),
                        "%02d:%02d:%02d", hours, minutes, seconds);
                countdownText.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                countdownText.setText("카운트다운 종료!");
            }
        }.start();
    }

    private void showTodayClothDialog() {
        Dialog dialog = new Dialog(d_day.this);
        dialog.setContentView(R.layout.today_cloth);

        Button checkFinish = dialog.findViewById(R.id.check_finish);
        checkFinish.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(d_day.this, After_dday.class);
            startActivity(intent);
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
