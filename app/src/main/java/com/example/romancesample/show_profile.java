package com.example.romancesample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class show_profile extends AppCompatActivity {

    private ImageView getfromCamera;
    private TextView takeCloneAge, takePlace;
    private EditText editCloneAge, editPlace;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        // --- 이미지 처리 ---
        getfromCamera = findViewById(R.id.getfromCamera);

        MyApplication app = (MyApplication) getApplication();
        String imageUri = app.getPhotoUri();

        if (imageUri != null) {
            Glide.with(this)
                    .load(imageUri)
                    .into(getfromCamera);
        }

        // --- 텍스트 수정 관련 요소 초기화 ---
        takeCloneAge = findViewById(R.id.take_cloneage);
        takePlace = findViewById(R.id.take_place);

        editCloneAge = findViewById(R.id.edit_cloneage);
        editPlace = findViewById(R.id.edit_place);

        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = button2.getText().toString();

                if (currentText.equals("수정")) {
                    // 기존 값 EditText에 복사
                    editCloneAge.setText(takeCloneAge.getText().toString());
                    editPlace.setText(takePlace.getText().toString());

                    // EditText 보이기, TextView 숨기기
                    takeCloneAge.setVisibility(View.GONE);
                    takePlace.setVisibility(View.GONE);

                    editCloneAge.setVisibility(View.VISIBLE);
                    editPlace.setVisibility(View.VISIBLE);

                    button2.setText("저장");

                } else if (currentText.equals("저장")) {
                    // 입력값 반영
                    takeCloneAge.setText(editCloneAge.getText().toString());
                    takePlace.setText(editPlace.getText().toString());

                    // TextView 보이기, EditText 숨기기
                    editCloneAge.setVisibility(View.GONE);
                    editPlace.setVisibility(View.GONE);

                    takeCloneAge.setVisibility(View.VISIBLE);
                    takePlace.setVisibility(View.VISIBLE);

                    button2.setText("수정");
                }
            }
        });
    }
}
