package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class check_camera extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_camera);

        // 앱 시작 시 바로 다이얼로그 띄우기
        showCustomDialog();
    }

    // 커스텀 다이얼로그를 띄우는 메서드
    private void showCustomDialog() {
        // 레이아웃 인플레이트
        LayoutInflater inflater = getLayoutInflater();
        android.view.View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        // 다이얼로그 빌더 사용
        AlertDialog.Builder builder = new AlertDialog.Builder(check_camera.this);
        builder.setView(dialogView);

        // 다이얼로그 커스텀 버튼 설정
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        // "확인" 버튼 클릭 시 CameraActivity로 이동
        btnConfirm.setOnClickListener(v -> {
            // 카메라 액티비티로 이동
            Intent intent = new Intent(check_camera.this, CameraActivity.class);
            startActivity(intent);
            finish();  // 현재 Activity 종료 (check_camera 종료)
        });

        // 다이얼로그 표시
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false); // 다이얼로그 밖을 클릭해도 닫히지 않도록 설정
        dialog.show();
    }
}
