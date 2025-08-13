package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class choose_km_age extends AppCompatActivity {

    private TextView showKm, showAge;
    private SeekBar kmSeekBar, ageSeekBar, ageSeekBar2;
    private SelectionData selectionData;

    int younger = 0;
    int older = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_km_age);

        selectionData = (SelectionData) getIntent().getSerializableExtra("selectionData");

        showKm = findViewById(R.id.showkm);
        showAge = findViewById(R.id.showage);
        //kmSeekBar = findViewById(R.id.kmseekBar);
        ageSeekBar = findViewById(R.id.ageseekBar);    // 연하
        ageSeekBar2 = findViewById(R.id.ageseekBar2);  // 연상
        ImageView next = findViewById(R.id.next4);

        // 거리 SeekBar
        kmSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showKm.setText(progress + " km");
                selectionData.km = progress;
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 연하 SeekBar
        ageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                younger = progress;
                updateAgeText();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 연상 SeekBar
        ageSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                older = progress;
                updateAgeText();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        next.setOnClickListener(v -> {
            Intent intent = new Intent(choose_km_age.this, check_camera.class);
            intent.putExtra("selectionData", selectionData);
            startActivity(intent);
        });
    }

    private void updateAgeText() {
        showAge.setText(younger + "살 연하 " + older + "살 연상");
        selectionData.chooseage = older; // 예시로 연상을 저장 (필요시 따로 저장 가능)
    }
}
