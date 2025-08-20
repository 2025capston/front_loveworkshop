package com.example.romancesample;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.api.UserApi;
import com.example.romancesample.api.RetrofitClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.net.Uri;
import android.widget.Toast;


public class choose_clone extends AppCompatActivity {

    private ImageView imageOfYourLuv;
    private ActivityResultLauncher<String> getImageLauncher;
    private Uri selectedImageUri = null;

    private Button womanButton;
    private Button manButton;
    private Button bothButton;

    private UserApi userApi;
    private String selectedGender = null; // 선택된 성별 저장
    private int olderThan = 0; // 예시 값
    private int youngerThan = 100; // 예시 값

    private void resetButtonColors() {
        int defaultColor = Color.parseColor("#F1ECE9"); // 기본 배경색
        int textColor = Color.parseColor("#7C5147");    // 기본 글자색

        womanButton.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
        womanButton.setTextColor(textColor);

        manButton.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
        manButton.setTextColor(textColor);

        bothButton.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
        bothButton.setTextColor(textColor);
    }

    private void highlightSelectedButton(Button selectedButton) {
        int selectedColor = Color.parseColor("#FFB6B6"); // 선택된 배경색
        int selectedTextColor = Color.WHITE;

        selectedButton.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
        selectedButton.setTextColor(selectedTextColor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_clone);

        userApi = RetrofitClient.getUserApi();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textView = findViewById(R.id.textView);
        SeekBar seekBar = findViewById(R.id.seekBar);
        imageOfYourLuv = findViewById(R.id.imageofyourLuv);
        Button inputClonePic = findViewById(R.id.input_clonepic);

        womanButton = findViewById(R.id.woman);
        manButton = findViewById(R.id.man);
        bothButton = findViewById(R.id.both);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = progress + 5;
                textView.setText("현재 값: " + value);
                olderThan = value;
                youngerThan = value + 10;

            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        getImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;
                        imageOfYourLuv.setImageURI(uri);
                    }
                });

        inputClonePic.setOnClickListener(v -> getImageLauncher.launch("image/*"));

        ImageView gotomatch = findViewById(R.id.gotomatch);
        gotomatch.setOnClickListener(v -> {
            Intent intent = new Intent(choose_clone.this, wait_match.class);
            if (selectedImageUri != null ) {

                intent.putExtra("image_uri", selectedImageUri.toString());
            }
            startActivity(intent);
        });

        womanButton.setOnClickListener(v -> {
            resetButtonColors();
            highlightSelectedButton(womanButton);
            selectedGender = "여성";
        });

        manButton.setOnClickListener(v -> {
            resetButtonColors();
            highlightSelectedButton(manButton);
            selectedGender = "남성";
        });

        bothButton.setOnClickListener(v -> {
            resetButtonColors();
            highlightSelectedButton(bothButton);
            selectedGender = "both";
        });
    }

    private File uriToFile(Uri uri) throws Exception {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File tempFile = new File(getCacheDir(), "temp_image");
        OutputStream outputStream = new FileOutputStream(tempFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();

        return tempFile;
    }
    private void uploadIdeal(Uri photoUri, String gender, int olderThan, int youngerThan) {
        try {
            File photoFile = uriToFile(photoUri);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), photoFile);
            MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo", photoFile.getName(), requestFile);

            RequestBody userIdPart = RequestBody.create(MediaType.parse("text/plain"), "1"); // 테스트용 userId
            RequestBody genderPart = RequestBody.create(MediaType.parse("text/plain"), gender);
            RequestBody olderPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(olderThan));
            RequestBody youngerPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(youngerThan));

            Call<ResponseBody> call = userApi.registerIdeal(userIdPart, photoPart, genderPart, olderPart, youngerPart);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(choose_clone.this, "이상형 등록 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(choose_clone.this, wait_match.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(choose_clone.this, "서버 오류: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(choose_clone.this, "업로드 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "파일 처리 오류", Toast.LENGTH_SHORT).show();
        }
    }

}
