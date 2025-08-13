package com.example.romancesample;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class choose_sex extends AppCompatActivity {
    SelectionData selectionData = new SelectionData();

    AppCompatButton femaleButton;
    AppCompatButton maleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sex);

        ImageView next = findViewById(R.id.next);

        femaleButton = findViewById(R.id.female);
        maleButton = findViewById(R.id.male);

        femaleButton.setOnClickListener(v -> {
            selectionData.gender = "여성";
            femaleButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.button_selected_color)));
        });

        maleButton.setOnClickListener(v -> {
            selectionData.gender = "남성";
            maleButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.button_selected_color)));
        });

        next.setOnClickListener(v -> {
            Intent intent = new Intent(choose_sex.this, choose_birth.class);
            intent.putExtra("selectionData", selectionData);
            startActivity(intent);
        });
    }
}
