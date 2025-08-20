package com.example.romancesample;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.ApiService;
import com.example.romancesample.api.DailyMission;
import com.example.romancesample.api.MissionComparison;
import com.example.romancesample.api.MissionResponse;
import com.example.romancesample.api.ScheduleOption;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dayN_mission extends AppCompatActivity {

    private TextView ddayText;
    private TextView missionText;
    private EditText missionAnswer;
    private ApiService apiService;
    private String token;
    private Long requestId; // 매칭 요청 ID
    private Long matchMissionId; // 서버에서 받아온 오늘 미션 ID
    private Long userId; // 현재 사용자 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_day_nmission);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ddayText = findViewById(R.id.ddaycheck);
        missionText = findViewById(R.id.landom_mission);
        missionAnswer = findViewById(R.id.mission_text);

        token = "Bearer " + getJwtToken();
        requestId = getCurrentMatchRequestId();
        userId = getCurrentUserId();

        apiService = ApiClient.getApiService();

        // 오늘 매칭 확정 일정 불러오기 → D-Day 계산 및 자동 이동
        loadConfirmedSchedule();

        // 오늘 배정된 데일리 미션 불러오기
        loadDailyMission();
    }

    private void loadConfirmedSchedule() {
        apiService.getConfirmedSchedule(token, requestId).enqueue(new Callback<ScheduleOption>() {
            @Override
            public void onResponse(Call<ScheduleOption> call, Response<ScheduleOption> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ScheduleOption option = response.body();
                    String displayTime = option.getDisplayTime(); // "8월 20일 15:00~16:00"

                    try {
                        String datePart = displayTime.split(" ")[0] + " " + displayTime.split(" ")[1]; // "8월 20일"
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate today = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일");
                            LocalDate targetDate = LocalDate.parse(datePart, formatter);
                            long daysBetween = ChronoUnit.DAYS.between(today, targetDate);

                            if (daysBetween > 0) {
                                ddayText.setText("D - " + daysBetween);
                            } else if (daysBetween == 0) {
                                ddayText.setText("D - Day");
                                // D-Day 당일 자동 이동
                                startActivity(new android.content.Intent(dayN_mission.this, d_day.class));
                                finish();
                            } else {
                                ddayText.setText("종료된 일정");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ddayText.setText("날짜 변환 오류");
                    }
                } else {
                    ddayText.setText("일정 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<ScheduleOption> call, Throwable t) {
                t.printStackTrace();
                ddayText.setText("네트워크 오류");
            }
        });
    }

    private void loadDailyMission() {
        apiService.getTodayMission(token, requestId).enqueue(new Callback<DailyMission>() {
            @Override
            public void onResponse(Call<DailyMission> call, Response<DailyMission> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DailyMission mission = response.body();
                    matchMissionId = mission.getId(); // 미션 ID 저장
                    missionText.setText(mission.getContent());
                } else {
                    missionText.setText("데일리 미션 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<DailyMission> call, Throwable t) {
                t.printStackTrace();
                missionText.setText("네트워크 오류");
            }
        });
    }

    // 미션 답안 제출
    private void submitMissionAnswer(String answer) {
        apiService.submitMissionResponse(token, matchMissionId, userId, answer)
                .enqueue(new Callback<MissionResponse>() {
                    @Override
                    public void onResponse(Call<MissionResponse> call, Response<MissionResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(dayN_mission.this, "답안 제출 완료", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(dayN_mission.this, "답안 제출 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MissionResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(dayN_mission.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // 상대 답안 확인
    private void showPartnerAnswer() {
        apiService.getMissionResponses(token, matchMissionId, userId)
                .enqueue(new Callback<MissionComparison>() {
                    @Override
                    public void onResponse(Call<MissionComparison> call, Response<MissionComparison> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String partnerAnswer = response.body().getPartnerResponse().getAnswer();
                            TodayAnswerDialog dialog = new TodayAnswerDialog(dayN_mission.this, partnerAnswer);
                            dialog.show();
                        } else {
                            Toast.makeText(dayN_mission.this, "상대 답안 불러오기 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MissionComparison> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(dayN_mission.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getJwtToken() { return "your_jwt_token_here"; }
    private Long getCurrentMatchRequestId() { return 1L; }
    private Long getCurrentUserId() { return 1L; }
}
