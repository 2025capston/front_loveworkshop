package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class choose_home extends AppCompatActivity {

    private Button cityButton, townButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_home);

        // 버튼 및 이미지 뷰 찾기
        cityButton = findViewById(R.id.city);
        townButton = findViewById(R.id.town);
        ImageView nextButton = findViewById(R.id.next10);

        // 도시 선택 버튼 동작
        cityButton.setOnClickListener(v -> showCityDialog());

        // 동네 선택 버튼 동작
        townButton.setOnClickListener(v -> showTownDialog());

        // 다음 화면 이동
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(choose_home.this, choose_luv.class);
            startActivity(intent);
        });

        // 인셋 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showCityDialog() {
        final String[] cities = {"서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("도시를 선택하세요")
                .setItems(cities, (dialog, which) -> cityButton.setText(cities[which]))
                .show();
    }

    private void showTownDialog() {
        final String[] towns = {"강남구", "서초구", "송파구", "강동구", "마포구", "용산구", "중구", "종로구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("동네를 선택하세요")
                .setItems(towns, (dialog, which) -> townButton.setText(towns[which]))
                .show();
    }
}
