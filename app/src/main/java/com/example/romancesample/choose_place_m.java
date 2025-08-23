package com.example.romancesample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class choose_place_m extends AppCompatActivity {

    TextView place1, place2, place3;
    LinearLayout linearLayout2;

    String selectedPlace = "";
    String selectedDateTime = "";

    TextView prevSelectedPlace = null;
    TextView prevSelectedDateTime = null;

    Button sendMatchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_place_m);

        // 뷰 바인딩
        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        place3 = findViewById(R.id.place3);
        linearLayout2 = findViewById(R.id.linearLayout2);
        sendMatchButton = findViewById(R.id.send_match);

        // 장소 선택 처리
        View.OnClickListener placeClickListener = v -> {
            TextView clicked = (TextView) v;
            if (prevSelectedPlace != null) {
                prevSelectedPlace.setTextColor(Color.BLACK); // 이전 항목 글씨 검정
            }
            clicked.setTextColor(Color.parseColor("#FF5A5A")); // 선택된 항목 글씨 핑크
            prevSelectedPlace = clicked;
            selectedPlace = clicked.getText().toString();
        };

        place1.setOnClickListener(placeClickListener);
        place2.setOnClickListener(placeClickListener);
        place3.setOnClickListener(placeClickListener);

        // 시간 항목 추가
        addDateTimeOptions();

        // 보내기 버튼 클릭 처리
        sendMatchButton.setOnClickListener(v -> {
            if (!selectedPlace.isEmpty() && !selectedDateTime.isEmpty()) {
                Intent intent = new Intent(choose_place_m.this, wait_day3.class);
                intent.putExtra("selected_place", selectedPlace);
                intent.putExtra("selected_datetime", selectedDateTime);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "장소와 날짜/시간을 선택해 주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDateTimeOptions() {
        String[] options = {"15시~16시", "16시~17시", "17시~18시"};
        for (String option : options) {
            TextView timeView = new TextView(this);
            timeView.setText(option);
            timeView.setTextSize(15);
            timeView.setBackgroundResource(R.drawable.whitebox);
            timeView.setPadding(20, 10, 20, 10);
            timeView.setTextColor(Color.BLACK);
            timeView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 16, 0, 0);
            timeView.setLayoutParams(params);

            timeView.setOnClickListener(v -> {
                if (prevSelectedDateTime != null) {
                    prevSelectedDateTime.setTextColor(Color.BLACK); // 이전 시간 항목 글씨 검정
                }
                timeView.setTextColor(Color.parseColor("#FF5A5A")); // 선택된 시간 글씨 핑크
                prevSelectedDateTime = timeView;
                selectedDateTime = timeView.getText().toString();
            });

            linearLayout2.addView(timeView);
        }
    }
}
