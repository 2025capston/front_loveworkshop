package com.example.romancesample;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.MatchRequest;
import com.example.romancesample.api.MatchResponse;

import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class show_matchPeople extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_match_people);

        // 하단 네비게이션 설정
        setupBottomNavigation(R.id.nav_luv);

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 버튼
        Button matchBtn = findViewById(R.id.matchthisperson);
        Button nExt = findViewById(R.id.nextideal);

        matchBtn.setOnClickListener(v -> showMatchDialog());
        nExt.setOnClickListener(v -> {
            Intent intent = new Intent(show_matchPeople.this, show_clones.class);
            startActivity(intent);
        });
    }

    private void showMatchDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.match_warn);

        // btnYes 클릭 시 매칭 신청 API 호출
        Button btnYes = dialog.findViewById(R.id.btnyes);
        btnYes.setOnClickListener(v -> {
            Long fromUserId = getLoggedInUserId(); // 로그인 유저 ID
            Long toUserId = getTargetUserId();     // 상대 유저 ID

            String token = "Bearer " + getJwtToken(); // JWT 토큰 가져오기

            // 메시지는 사용자에게 보여주지 않음, 서버에서 PENDING 처리
            MatchRequest request = new MatchRequest(fromUserId, toUserId, "");

            ApiClient.getApiService().createMatchRequest(token, request)
                    .enqueue(new Callback<MatchResponse>() {
                        @Override
                        public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                // 성공 시 다음 화면 이동
                                Intent intent = new Intent(show_matchPeople.this, check_list_of_people.class);
                                startActivity(intent);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(show_matchPeople.this, "매칭 신청 실패", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MatchResponse> call, Throwable t) {
                            Toast.makeText(show_matchPeople.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        dialog.show();
    }

    // TODO: 실제 구현 필요
    private Long getLoggedInUserId() {
        // 로그인한 유저의 ID를 SharedPreferences 등에서 가져오기
        return 1L;
    }

    private Long getTargetUserId() {
        // 현재 매칭 대상 유저의 ID
        return 2L;
    }

    private String getJwtToken() {
        // 로그인 후 저장된 JWT 토큰 가져오기
        return "your_jwt_token_here";
    }
}
