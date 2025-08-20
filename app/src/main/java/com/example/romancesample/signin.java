package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.romancesample.api.ApiClient;
import com.example.romancesample.api.UserApi;
import com.example.romancesample.model.UserDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signin extends AppCompatActivity {

    private EditText inputEmail, inputName, inputPw, checkPw, inputPhonenum;
    private EditText inputAuthCode;
    private TextView timerText;
    private Button checkemail, verifyCodeButton, signintologin;
    private CountDownTimer countDownTimer;

    private boolean isEmailVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputEmail = findViewById(R.id.inputEmail);
        inputName = findViewById(R.id.inputName);
        inputPw = findViewById(R.id.inputPw);
        checkPw = findViewById(R.id.checkPw);
        inputPhonenum = findViewById(R.id.inputPhonenum);
        inputAuthCode = findViewById(R.id.inputAuthCode);
        timerText = findViewById(R.id.timerText);
        checkemail = findViewById(R.id.checkemail);
        verifyCodeButton = findViewById(R.id.verifyCodeButton);
        signintologin = findViewById(R.id.signintologin);

        inputAuthCode.setVisibility(View.GONE);
        timerText.setVisibility(View.GONE);
        verifyCodeButton.setVisibility(View.GONE);

        checkemail.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(signin.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            UserApi api = ApiClient.getClient().create(UserApi.class);
            api.sendAuthEmail(email).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(signin.this, "인증 이메일이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        inputAuthCode.setVisibility(View.VISIBLE);
                        timerText.setVisibility(View.VISIBLE);
                        verifyCodeButton.setVisibility(View.VISIBLE);
                        inputAuthCode.setEnabled(true);
                        verifyCodeButton.setEnabled(true);
                        isEmailVerified = false;
                        startTimer(180000); // 3분
                    } else {
                        Toast.makeText(signin.this, "이메일 전송 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(signin.this, "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        verifyCodeButton.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String code = inputAuthCode.getText().toString().trim();

            if (code.isEmpty()) {
                Toast.makeText(signin.this, "인증 코드를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            UserApi api = ApiClient.getClient().create(UserApi.class);
            api.verifyCode(email, code).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                        Toast.makeText(signin.this, "이메일 인증 성공!", Toast.LENGTH_SHORT).show();
                        if (countDownTimer != null) countDownTimer.cancel();
                        timerText.setVisibility(View.GONE);
                        inputAuthCode.setEnabled(false);
                        verifyCodeButton.setEnabled(false);
                        isEmailVerified = true;
                    } else {
                        Toast.makeText(signin.this, "인증 코드가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(signin.this, "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        signintologin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String name = inputName.getText().toString().trim();
            String password = inputPw.getText().toString().trim();
            String confirmPassword = checkPw.getText().toString().trim();
            String phone = inputPhonenum.getText().toString().trim();

            if (email.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(signin.this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(signin.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }



            UserDTO user = new UserDTO(email, name, password, phone);
            UserApi api = ApiClient.getClient().create(UserApi.class);
            api.registerUser(user).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String msg = response.body();
                        Toast.makeText(signin.this, msg, Toast.LENGTH_SHORT).show();
                        if ("회원가입 성공".equals(msg)) {
                            startActivity(new Intent(signin.this, login.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(signin.this, "회원가입 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(signin.this, "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void startTimer(long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long sec = seconds % 60;
                timerText.setText(String.format("%02d:%02d", minutes, sec));
            }

            @Override
            public void onFinish() {
                timerText.setText("시간 초과");
                verifyCodeButton.setEnabled(false);
            }
        }.start();
    }
}
