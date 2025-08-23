package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.romancesample.model.UserProfileDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class choose_birth extends AppCompatActivity {
    UserProfileDTO profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_birth);

        // 이전 Activity에서 전달받은 DTO
        profile = getIntent().getParcelableExtra("profile");
        if(profile == null) {
            profile = new UserProfileDTO();
        }

        Button birthButton = findViewById(R.id.city);
        birthButton.setOnClickListener(v -> showYearPickerDialog(birthButton));

        ImageView next = findViewById(R.id.next1);
        next.setOnClickListener(v -> {
            String yearStr = birthButton.getText().toString();
            if (!yearStr.matches("\\d{4}")) {
                Toast.makeText(this, "연도를 선택하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            int birthYear = Integer.parseInt(yearStr);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            profile.setAge(currentYear - birthYear);  // 나이 계산해서 DTO에 저장

            // 다음 Activity로 DTO 전달
            Intent intent = new Intent(choose_birth.this, choose_bdypro.class);
            intent.putExtra("profile", profile);
            startActivity(intent);
        });
    }

    private void showYearPickerDialog(Button birthButton) {
        List<String> years = new ArrayList<>();
        for (int i = 2006; i >= 1950; i--) {
            years.add(String.valueOf(i));
        }

        Spinner yearSpinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearSpinner.setAdapter(adapter);

        new AlertDialog.Builder(this)
                .setTitle("연도 선택")
                .setView(yearSpinner)
                .setPositiveButton("확인", (dialog, which) -> {
                    String selectedYear = (String) yearSpinner.getSelectedItem();
                    if (selectedYear != null) {
                        birthButton.setText(selectedYear);
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }
}
