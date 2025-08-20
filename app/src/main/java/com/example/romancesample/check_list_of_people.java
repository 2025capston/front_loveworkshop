package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.ApiService;
import com.example.romancesample.api.MatchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class check_list_of_people extends AppCompatActivity {

    private LinearLayout pendLayout;       // 받은 신청
    private LinearLayout getPendLayout;    // 보낸 신청
    private TextView noPeopleText;

    private ApiService apiService;
    private String token = "Bearer YOUR_JWT_TOKEN"; // 실제 토큰으로 교체
    private Long myUserId = 1L; // 로그인 유저 ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_of_people);

        pendLayout = findViewById(R.id.pend);
        getPendLayout = findViewById(R.id.getpend);
        noPeopleText = findViewById(R.id.nopeople);

        // 하단 네비게이션
        setupBottomNavigation(R.id.nav_alarm2);

        // 시스템 바 패딩
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiService = ApiClient.getApiService();

        loadReceivedRequests();
        loadSentRequests();
    }

    private void loadReceivedRequests() {
        apiService.getMatchRequests(token).enqueue(new Callback<List<MatchResponse>>() {
            @Override
            public void onResponse(Call<List<MatchResponse>> call, Response<List<MatchResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(check_list_of_people.this, "받은 신청 불러오기 실패", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<MatchResponse> allRequests = response.body();
                pendLayout.removeAllViews();
                boolean hasReceived = false;

                for (MatchResponse item : allRequests) {
                    if (item.getToUserId().equals(myUserId)) { // 받은 신청
                        hasReceived = true;
                        View child = LayoutInflater.from(check_list_of_people.this)
                                .inflate(R.layout.custom_check_list, pendLayout, false);

                        ImageView profile = child.findViewById(R.id.profileimg);
                        TextView height = child.findViewById(R.id.height1);
                        TextView live = child.findViewById(R.id.live1);
                        Button accept = child.findViewById(R.id.append1);
                        Button reject = child.findViewById(R.id.endappend1);

                        Glide.with(check_list_of_people.this).load(item.getProfileUrl()).into(profile);
                        height.setText(item.getHeight());
                        live.setText(item.getLive());

                        accept.setOnClickListener(v -> {
                            apiService.acceptMatch(token, item.getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Intent intent = new Intent(check_list_of_people.this, match_complete.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(check_list_of_people.this, "수락 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(check_list_of_people.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });

                        reject.setOnClickListener(v -> {
                            apiService.rejectMatch(token, item.getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        pendLayout.removeView(child);
                                        if (pendLayout.getChildCount() == 0) noPeopleText.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(check_list_of_people.this, "거절 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(check_list_of_people.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });

                        pendLayout.addView(child);
                    }
                }

                noPeopleText.setVisibility(hasReceived ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<MatchResponse>> call, Throwable t) {
                Toast.makeText(check_list_of_people.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSentRequests() {
        apiService.getMatchRequests(token).enqueue(new Callback<List<MatchResponse>>() {
            @Override
            public void onResponse(Call<List<MatchResponse>> call, Response<List<MatchResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(check_list_of_people.this, "보낸 신청 불러오기 실패", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<MatchResponse> allRequests = response.body();
                getPendLayout.removeAllViews();

                for (MatchResponse item : allRequests) {
                    if (item.getFromUserId().equals(myUserId)) { // 보낸 신청
                        View child = LayoutInflater.from(check_list_of_people.this)
                                .inflate(R.layout.custom_append_list, getPendLayout, false);

                        ImageView profile = child.findViewById(R.id.profileimg);
                        TextView height = child.findViewById(R.id.height1);
                        TextView live = child.findViewById(R.id.live1);
                        Button cancel = child.findViewById(R.id.endA);

                        Glide.with(check_list_of_people.this).load(item.getProfileUrl()).into(profile);
                        height.setText(item.getHeight());
                        live.setText(item.getLive());

                        cancel.setOnClickListener(v -> {
                            apiService.rejectMatch(token, item.getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        getPendLayout.removeView(child);
                                    } else {
                                        Toast.makeText(check_list_of_people.this, "취소 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(check_list_of_people.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                                }
                            });
                        });

                        getPendLayout.addView(child);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MatchResponse>> call, Throwable t) {
                Toast.makeText(check_list_of_people.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBottomNavigation(int selectedId) {
        // BottomNavigationView 설정
    }
}
