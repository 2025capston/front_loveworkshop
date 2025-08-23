package com.example.romancesample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.ReportApi;
import com.example.romancesample.model.ReportRequest;
import com.example.romancesample.model.ReportResponse;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BadPeople extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_people);

        // 하단 네비게이션 설정
        setupBottomNavigation(R.id.nav_alarm2);

        // 버튼 및 입력란 참조
        Button btnOther = findViewById(R.id.btn_other);
        EditText editTextOtherReason = findViewById(R.id.edittext_other_reason);
        Button btnGoToStart = findViewById(R.id.gotostart);

        // 신고 버튼들을 리스트로 정리
        List<Button> reportButtons = Arrays.asList(
                findViewById(R.id.btn_different_photo),
                findViewById(R.id.btn_no_show),
                findViewById(R.id.btn_rude_behavior),
                findViewById(R.id.btn_violent),
                findViewById(R.id.btn_false_info),
                findViewById(R.id.btn_illegal_request),
                findViewById(R.id.btn_spam),
                btnOther
        );

        // Retrofit ReportApi 생성
        ReportApi reportApi = ApiClient.getClient().create(ReportApi.class);

        // 각 버튼에 클릭 리스너 추가
        for (Button button : reportButtons) {
            button.setOnClickListener(v -> {
                // 모든 버튼의 글자색을 기본값(검정)으로 초기화
                for (Button b : reportButtons) {
                    b.setTextColor(Color.BLACK);
                }

                // 선택된 버튼의 글자색 변경
                button.setTextColor(Color.parseColor("#FF4081")); // 예: 빨간색

                // '기타' 버튼 클릭 시 EditText 보이기 / 숨기기
                if (button == btnOther) {
                    editTextOtherReason.setVisibility(View.VISIBLE);
                } else {
                    editTextOtherReason.setVisibility(View.GONE);
                }

                // 선택된 사유 문자열 가져오기
                String reason;
                if (button == btnOther) {
                    reason = editTextOtherReason.getText().toString().trim();
                    if (reason.isEmpty()) {
                        editTextOtherReason.setError("신고 사유를 입력하세요");
                        return;
                    }
                } else {
                    reason = button.getText().toString();
                }

                // 신고 요청 객체 생성
                ReportRequest reportRequest = new ReportRequest(
                        12, // requestId (실제 값으로 바꿀 것)
                        3,  // reporterUserId (로그인 사용자 ID로 바꿀 것)
                        reason,
                        reason // 상세 내용 동일하게 넣음, 필요 시 다른 입력값 사용
                );

                // 서버에 신고 등록 요청
                reportApi.createReport(reportRequest).enqueue(new Callback<ReportResponse>() {
                    @Override
                    public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(BadPeople.this,
                                    "신고 등록 성공: " + response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BadPeople.this,
                                    "신고 등록 실패, 상태 코드: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReportResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(BadPeople.this,
                                "신고 요청 중 오류 발생",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }

        // "다시 이상형 찾으러 가기" 버튼 동작
        btnGoToStart.setOnClickListener(v -> {
            Intent intent = new Intent(BadPeople.this, choose_clone.class);
            startActivity(intent);
        });

        // 시스템 바(상단바, 하단바)와 겹치지 않도록 패딩 조정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
