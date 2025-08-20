package com.example.romancesample;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.ApiService;
import com.example.romancesample.api.ScheduleOption;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class choose_place_m extends AppCompatActivity {

    TextView place1, place2, place3;
    LinearLayout linearLayout2;
    Button sendMatchButton;

    String selectedPlace = "";
    ScheduleOption selectedOption = null;

    TextView prevSelectedPlace = null;
    TextView prevSelectedDateTime = null;

    ArrayList<ScheduleOption> scheduleOptions; // 서버에서 가져온 일정 옵션 리스트
    Long requestId = 1L; // 실제 매칭 요청 ID
    String token = "Bearer " + getJwtToken();

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

        // 서버에서 일정 옵션 받아오기
        fetchScheduleOptions();

        // 장소 선택 처리
        place1.setOnClickListener(v -> selectPlace(place1));
        place2.setOnClickListener(v -> selectPlace(place2));
        place3.setOnClickListener(v -> selectPlace(place3));

        // 보내기 버튼 클릭
        sendMatchButton.setOnClickListener(v -> {
            if (selectedOption != null) {
                confirmSchedule(selectedOption);
            } else {
                Toast.makeText(this, "장소와 날짜/시간을 선택해 주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectPlace(TextView placeView) {
        if (prevSelectedPlace != null) prevSelectedPlace.setTextColor(Color.BLACK);
        placeView.setTextColor(Color.parseColor("#FF5A5A"));
        prevSelectedPlace = placeView;
        selectedPlace = placeView.getText().toString();

        // 선택된 장소가 바뀌면 시간 선택 초기화
        prevSelectedDateTime = null;
        selectedOption = null;
    }

    private void fetchScheduleOptions() {
        ApiService apiService = ApiClient.getApiService();
        apiService.getProposedSchedule(token, requestId).enqueue(new Callback<ArrayList<ScheduleOption>>() {
            @Override
            public void onResponse(Call<ArrayList<ScheduleOption>> call, Response<ArrayList<ScheduleOption>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    scheduleOptions = response.body();

                    // 장소 텍스트 설정 (서버에서 받아온 places)
                    if (!scheduleOptions.isEmpty()) {
                        place1.setText(scheduleOptions.get(0).getDisplayPlace());
                        place2.setText(scheduleOptions.get(1).getDisplayPlace());
                        place3.setText(scheduleOptions.get(2).getDisplayPlace());
                    }

                    addDateTimeOptions();
                } else {
                    Toast.makeText(choose_place_m.this, "일정 옵션을 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ScheduleOption>> call, Throwable t) {
                Toast.makeText(choose_place_m.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDateTimeOptions() {
        if(scheduleOptions == null) return;

        for(ScheduleOption option : scheduleOptions) {
            TextView timeView = new TextView(this);
            timeView.setText(option.getDisplayTime());
            timeView.setTextSize(15);
            timeView.setBackgroundResource(R.drawable.whitebox);
            timeView.setPadding(20, 10, 20, 10);
            timeView.setTextColor(Color.BLACK);
            timeView.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 16, 0, 0);
            timeView.setLayoutParams(params);

            timeView.setOnClickListener(v -> {
                if(selectedPlace.isEmpty()) {
                    Toast.makeText(this, "장소를 먼저 선택해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(prevSelectedDateTime != null) prevSelectedDateTime.setTextColor(Color.BLACK);
                timeView.setTextColor(Color.parseColor("#FF5A5A"));
                prevSelectedDateTime = timeView;

                // 선택된 옵션 찾기
                if(option.getDisplayPlace().equals(selectedPlace) &&
                        option.getDisplayTime().equals(timeView.getText().toString())) {
                    selectedOption = option;
                }
            });

            linearLayout2.addView(timeView);
        }
    }

    private void confirmSchedule(ScheduleOption option) {
        ApiClient.getApiService().confirmSchedule(token, requestId,
                option.getDateOptionId(),
                option.getTimeOptionId(),
                option.getPlaceOptionId()
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(choose_place_m.this, "일정이 확정되었습니다!", Toast.LENGTH_SHORT).show();
                    startActivity(new android.content.Intent(choose_place_m.this, wait_day3.class));
                    finish();
                } else {
                    Toast.makeText(choose_place_m.this, "서버 오류로 일정 확정 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(choose_place_m.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static String getJwtToken() {
        return "your_jwt_token_here";
    }
}
