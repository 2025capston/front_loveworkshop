package com.example.romancesample;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.ApiService;
import com.example.romancesample.api.OutfitSubmission;
import com.example.romancesample.api.ScheduleOption;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class d_day extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1001;

    private TextView countdownText;
    private Button chooseOotdBtn, checkClothBtn;
    private ImageView imagePreview;

    private ApiService apiService;

    private String token = "Bearer YOUR_JWT_TOKEN";
    private long matchRequestId = 1L; // 같은 매칭의 두 유저가 공유하는 ID
    private Long myUserId = 1L;

    private long countdownMillis;
    private Uri pendingImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dday);

        // 하단 네비게이션 설정
        setupBottomNavigation(R.id.nav_alarm2);

        // 시스템 바 패딩 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        countdownText = findViewById(R.id.countdown_text);
        chooseOotdBtn = findViewById(R.id.choose_ootd);
        checkClothBtn = findViewById(R.id.check_cloth);
        imagePreview = findViewById(R.id.imagesetootd);

        apiService = ApiClient.getApiService();

        try {
            loadConfirmedSchedule();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        chooseOotdBtn.setOnClickListener(v -> openGallery());
        checkClothBtn.setOnClickListener(v -> {
            try {
                fetchAndShowPartnerOotd();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // ---------------------------
    // 일정 가져오기 & 카운트다운
    // ---------------------------
    private void loadConfirmedSchedule() throws IOException {
        Call<ScheduleOption> call = apiService.getConfirmedSchedule(token, matchRequestId);
        call.enqueue(new Callback<ScheduleOption>() {
            @Override
            public void onResponse(Call<ScheduleOption> call, Response<ScheduleOption> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(d_day.this, "일정 불러오기 실패", Toast.LENGTH_SHORT).show();
                    return;
                }

                ScheduleOption option = response.body();
                String displayTime = option.getDisplayTime(); // "15:00~16:00" 형태 가정

                try {
                    String startTimeStr = displayTime.split("~")[0]; // "15:00"
                    String[] hm = startTimeStr.split(":");
                    int hour = Integer.parseInt(hm[0]);
                    int minute = Integer.parseInt(hm[1]);

                    long nowMillis = System.currentTimeMillis();
                    long targetMillis = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        targetMillis = (hour * 3600 + minute * 60) * 1000L
                                - (LocalTime.now().toSecondOfDay() * 1000L);
                    }

                    if (targetMillis < 0) targetMillis = 0;

                    startCountdown(targetMillis);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(d_day.this, "시간 파싱 오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleOption> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(d_day.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startCountdown(long millis) {
        countdownMillis = millis;

        new android.os.CountDownTimer(countdownMillis, 1000) {
            @Override
            public void onTick(long ms) {
                long totalSec = ms / 1000;
                long h = totalSec / 3600;
                long m = (totalSec % 3600) / 60;
                long s = totalSec % 60;
                countdownText.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", h, m, s));
            }

            @Override
            public void onFinish() {
                countdownText.setText("00:00:00");
                // 약속 시간 이후 5시간 뒤 after_Dday로 이동
                new android.os.Handler().postDelayed(() -> {
                    Intent intent = new Intent(d_day.this, After_dday.class);
                    startActivity(intent);
                    finish();
                }, 5 * 3600 * 1000); // 5시간
            }
        }.start();
    }

    // ---------------------------
    // 갤러리 선택 → 착장 제출 (Multipart)
    // ---------------------------
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            pendingImageUri = data.getData();
            if (pendingImageUri != null) {
                imagePreview.setImageURI(pendingImageUri);
                submitOutfitMultipart(pendingImageUri);
            }
        }
    }

    private void submitOutfitMultipart(Uri imageUri) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("착장 등록 중...");
        pd.setCancelable(false);
        pd.show();

        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), bytes);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", "ootd.jpg", reqFile);

            // 서버 API 직접 호출
            apiService.submitOutfit(token, matchRequestId, myUserId, body)
                    .enqueue(new Callback<OutfitSubmission>() {
                        @Override
                        public void onResponse(Call<OutfitSubmission> call, Response<OutfitSubmission> response) {
                            pd.dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(d_day.this, "착장 등록 완료!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(d_day.this, "착장 등록 실패", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<OutfitSubmission> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(d_day.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            pd.dismiss();
            Toast.makeText(this, "파일 처리 오류", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // ---------------------------
    // 상대 착장 확인
    // ---------------------------
    private void fetchAndShowPartnerOotd() throws IOException {
        apiService.getOutfit(token, matchRequestId, myUserId)
                .enqueue(new Callback<OutfitSubmission>() {
                    @Override
                    public void onResponse(Call<OutfitSubmission> call, Response<OutfitSubmission> response) {
                        if (!response.isSuccessful() || response.body() == null || response.body().getImageUrl() == null) {
                            Toast.makeText(d_day.this, "상대 사진이 아직 없어요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        showPartnerOotdDialog(response.body().getImageUrl());
                    }

                    @Override
                    public void onFailure(Call<OutfitSubmission> call, Throwable t) {
                        Toast.makeText(d_day.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showPartnerOotdDialog(String imageUrl) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.today_cloth);

        ImageView ootdImg = dialog.findViewById(R.id.ootd);
        Button ok = dialog.findViewById(R.id.check_finish);

        Glide.with(this).load(imageUrl).into(ootdImg);

        ok.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
