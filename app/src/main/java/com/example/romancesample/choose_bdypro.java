package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.romancesample.model.UserProfileDTO;

public class choose_bdypro extends AppCompatActivity {
    UserProfileDTO profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bdypro);

        // 이전 페이지에서 전달받은 DTO
        profile = getIntent().getParcelableExtra("profile");
        if (profile == null) {
            profile = new UserProfileDTO();
        }

        ImageView next = findViewById(R.id.next2);
        EditText heightInput = findViewById(R.id.editTextNumber2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heightStr = heightInput.getText().toString().trim();

                if (heightStr.isEmpty()) {
                    Toast.makeText(choose_bdypro.this, "키를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    profile.setHeight(Integer.parseInt(heightStr));  // DTO에 키 저장
                } catch (NumberFormatException e) {
                    Toast.makeText(choose_bdypro.this, "숫자로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 다음 페이지로 DTO 전달
                Intent intent = new Intent(choose_bdypro.this, choose_home.class);
                intent.putExtra("profile", profile);
                startActivity(intent);
            }
        });
    }
}
