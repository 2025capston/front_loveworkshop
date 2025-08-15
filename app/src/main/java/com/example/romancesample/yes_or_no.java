package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class yes_or_no extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yes_or_no);

        // Get references to the buttons
        Button btnKeepMatching = findViewById(R.id.inginging);
        Button btnCancelMatching = findViewById(R.id.idontlikehim);

        // Set OnClickListener for "매칭 유지" button (keep matching)
        btnKeepMatching.setOnClickListener(v -> {
            // Start LastMissionActivity when "매칭 유지" button is clicked
            Intent intent = new Intent(yes_or_no.this, LastMission.class);
            startActivity(intent);
        });

        // Set OnClickListener for "매칭 해지" button (cancel matching)
        btnCancelMatching.setOnClickListener(v -> {
            // Start BadPeopleActivity when "매칭 해지" button is clicked
            Intent intent = new Intent(yes_or_no.this, BadPeople.class);
            startActivity(intent);
        });

        // Set window insets for padding to avoid overlap with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
