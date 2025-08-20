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

public class choose_clone extends AppCompatActivity {

    private ImageView imageOfYourLuv;
    private ActivityResultLauncher<String> getImageLauncher;
    private Uri selectedImageUri = null;

    private Button womanButton;
    private Button manButton;
    private Button bothButton;

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
            if (selectedImageUri != null) {
                intent.putExtra("image_uri", selectedImageUri.toString());
            }
            startActivity(intent);
        });

        womanButton.setOnClickListener(v -> {
            resetButtonColors();
            highlightSelectedButton(womanButton);
        });

        manButton.setOnClickListener(v -> {
            resetButtonColors();
            highlightSelectedButton(manButton);
        });

        bothButton.setOnClickListener(v -> {
            resetButtonColors();
            highlightSelectedButton(bothButton);
        });
    }
}
