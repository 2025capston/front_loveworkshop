package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.ApiService;
import com.example.romancesample.model.MatchKeepStatusRequestDTO;
import com.example.romancesample.model.MatchKeepStatusResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class yes_or_no extends AppCompatActivity {

    private Button btnKeepMatching;
    private Button btnCancelMatching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_or_no);

        btnKeepMatching = findViewById(R.id.inginging);
        btnCancelMatching = findViewById(R.id.idontlikehim);

        // ✅ "매칭 유지" 버튼 클릭
        btnKeepMatching.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            MatchKeepStatusRequestDTO request = new MatchKeepStatusRequestDTO("KEEP");

            apiService.updateMatchKeepStatus(10, request).enqueue(new Callback<MatchKeepStatusResponseDTO>() {
                @Override
                public void onResponse(Call<MatchKeepStatusResponseDTO> call, Response<MatchKeepStatusResponseDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // 서버 응답 성공 시 LastMission 화면 이동
                        Intent intent = new Intent(yes_or_no.this, LastMission.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<MatchKeepStatusResponseDTO> call, Throwable t) {
                    // 네트워크 실패 처리
                }
            });
        });

        // ✅ "매칭 해지" 버튼 클릭
        btnCancelMatching.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            MatchKeepStatusRequestDTO request = new MatchKeepStatusRequestDTO("END");

            apiService.updateMatchKeepStatus(10, request).enqueue(new Callback<MatchKeepStatusResponseDTO>() {
                @Override
                public void onResponse(Call<MatchKeepStatusResponseDTO> call, Response<MatchKeepStatusResponseDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // 서버 응답 성공 시 BadPeople 화면 이동
                        Intent intent = new Intent(yes_or_no.this, BadPeople.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<MatchKeepStatusResponseDTO> call, Throwable t) {
                    // 네트워크 실패 처리
                }
            });
        });

        // 시스템 바 패딩 처리
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
