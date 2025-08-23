package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.model.MeetingResultRequestDTO;
import com.example.romancesample.model.MeetingResultResponseDTO;
import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class After_dday extends BaseActivity {

    private Button okMeetHimBtn;
    private Button heIsBasBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_dday);

        // 하단 네비게이션 설정 (nav_alarm2를 현재 선택된 메뉴로 설정)
        setupBottomNavigation(R.id.nav_alarm2);

        // 시스템 바 패딩 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        okMeetHimBtn = findViewById(R.id.ok_meethim);
        heIsBasBtn = findViewById(R.id.heisbas);


        okMeetHimBtn.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            MeetingResultRequestDTO request = new MeetingResultRequestDTO("SUCCESS");

            apiService.updateMeetingResult(10, request).enqueue(new Callback<MeetingResultResponseDTO>() {
                @Override
                public void onResponse(Call<MeetingResultResponseDTO> call, Response<MeetingResultResponseDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Intent intent = new Intent(After_dday.this, meet_end.class);
                        startActivity(intent);
                    } else {
                        // 서버에서 에러 응답 온 경우 처리
                    }
                }

                @Override
                public void onFailure(Call<MeetingResultResponseDTO> call, Throwable t) {
                    // 네트워크 실패 처리
                }
            });
        });


        heIsBasBtn.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            MeetingResultRequestDTO request = new MeetingResultRequestDTO("FAIL");

            apiService.updateMeetingResult(10, request).enqueue(new Callback<MeetingResultResponseDTO>() {
                @Override
                public void onResponse(Call<MeetingResultResponseDTO> call, Response<MeetingResultResponseDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Intent intent = new Intent(After_dday.this, BadPeople.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<MeetingResultResponseDTO> call, Throwable t) {
                    // 네트워크 실패 처리
                }
            });
        });
    }
}
