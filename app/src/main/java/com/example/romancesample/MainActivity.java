package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.romancesample.api.ApiService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 서버 연결 테스트
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<MyResponse> call = apiService.testConnection();  // MyResponse 객체로 수정

        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.isSuccessful()) {
                    MyResponse result = response.body();
                    Log.d(TAG, "서버 응답: " + result.getMessage());
                    Toast.makeText(MainActivity.this, "서버 응답: " + result.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "서버 연결 실패 (응답 코드: " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.e(TAG, "서버 연결 실패", t);
            }
        });

        // 5초 후에 start.class로 넘어가기
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, start.class);  // StartActivity로 변경
                startActivity(intent);
                finish();  // MainActivity 종료
            }
        }, 3000);  // 5000ms = 5초
    }
}
