package com.example.romancesample;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.ApiService;
import com.example.romancesample.api.ScheduleOption;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class wait_day3 extends BaseActivity {

    TextView updatePlace, updateTime;
    Long requestId = 1L; // 실제 매칭 요청 ID 사용
    String token = "Bearer " + getJwtToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_day3);

        // 하단 네비게이션 적용
        setupBottomNavigation(R.id.nav_promise);

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 뷰 바인딩
        updatePlace = findViewById(R.id.update_place);
        updateTime = findViewById(R.id.update_time);

        // 사용자2가 확정한 일정 가져오기
        fetchConfirmedSchedule();
    }

    private void fetchConfirmedSchedule() {
        ApiService apiService = ApiClient.getApiService();

        apiService.getConfirmedSchedule(token, requestId).enqueue(new Callback<ScheduleOption>() {
            @Override
            public void onResponse(Call<ScheduleOption> call, Response<ScheduleOption> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ScheduleOption option = response.body();
                    // 날짜와 시간 / 장소 각각 표시
                    updateTime.setText(option.getDisplayTime());
                    updatePlace.setText(option.getDisplayPlace());
                } else {
                    Toast.makeText(wait_day3.this, "일정 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleOption> call, Throwable t) {
                Toast.makeText(wait_day3.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getJwtToken() {
        return "your_jwt_token_here";
    }
}
