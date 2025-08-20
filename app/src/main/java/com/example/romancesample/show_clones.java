package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.api.RetrofitClient;
import com.example.romancesample.api.UserApi;
import com.example.romancesample.model.IdealMatch;
import com.example.romancesample.model.IdealMatchListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class show_clones extends BaseActivity {

    private UserApi userApi;
    private List<CloneItem> cloneList = new ArrayList<>();
    private CloneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clones);

        // 하단 네비게이션
        setupBottomNavigation(R.id.nav_luv);

        // 시스템 바 패딩
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ListView & Adapter 초기화
        ListView listView = findViewById(R.id.clone_listview);
        adapter = new CloneAdapter(this, cloneList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(show_clones.this, show_matchPeople.class);
            startActivity(intent);
        });

        // Retrofit 초기화
        userApi = RetrofitClient.getUserApi();

        // 서버에서 매칭 데이터 가져오기
        fetchMatches();
    }

    private void fetchMatches() {
        Call<IdealMatchListResponse> call = userApi.getMatches("1"); // 테스트용 userId=1
        call.enqueue(new Callback<IdealMatchListResponse>() {
            @Override
            public void onResponse(Call<IdealMatchListResponse> call, Response<IdealMatchListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cloneList.clear();

                    for (IdealMatch match : response.body().getMatches()) {
                        String info = match.getHeight() + "cm " + match.getAge() + "세";
                        String distance = "   " + match.getDistance() + "km";

                        // 이미지 URL은 서버에서 내려오는 경우 Glide/Coil로 처리 필요
                        cloneList.add(new CloneItem(R.drawable.yug, info, distance));
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(show_clones.this, "서버 오류: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IdealMatchListResponse> call, Throwable t) {
                Toast.makeText(show_clones.this, "API 호출 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
