package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class choose_place extends AppCompatActivity {

    private static final int MAP_REQUEST_CODE = 1;
    private EditText locationEditText1, locationEditText2, locationEditText3;
    private String currentFieldId;
    private ArrayList<String> dateTimeList;
    private Button sendPlaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_place);

        dateTimeList = getIntent().getStringArrayListExtra("datetime_list");

        locationEditText1 = findViewById(R.id.locationEditText1);
        locationEditText2 = findViewById(R.id.locationEditText2);
        locationEditText3 = findViewById(R.id.locationEditText3);
        sendPlaceButton = findViewById(R.id.send_place);

        locationEditText1.setFocusable(false);
        locationEditText1.setOnClickListener(view -> openMap("LOCATION_1"));
        locationEditText2.setFocusable(false);
        locationEditText2.setOnClickListener(view -> openMap("LOCATION_2"));
        locationEditText3.setFocusable(false);
        locationEditText3.setOnClickListener(view -> openMap("LOCATION_3"));

        sendPlaceButton.setOnClickListener(view -> {
            if (areAllLocationsFilled()) {
                moveToNextActivity();
            } else {
                Toast.makeText(choose_place.this, "모든 장소를 입력해 주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMap(String fieldId) {
        currentFieldId = fieldId;
        Intent intent = new Intent(choose_place.this, MapActivity.class);
        intent.putExtra("field_id", fieldId);
        startActivityForResult(intent, MAP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedLocation = data.getStringExtra("selected_location");
            String fieldId = data.getStringExtra("field_id");

            if (fieldId != null) {
                switch (fieldId) {
                    case "LOCATION_1":
                        locationEditText1.setText(selectedLocation);
                        break;
                    case "LOCATION_2":
                        locationEditText2.setText(selectedLocation);
                        break;
                    case "LOCATION_3":
                        locationEditText3.setText(selectedLocation);
                        break;
                }
            }
        }
    }

    private boolean areAllLocationsFilled() {
        return !locationEditText1.getText().toString().isEmpty() &&
                !locationEditText2.getText().toString().isEmpty() &&
                !locationEditText3.getText().toString().isEmpty();
    }

    private void moveToNextActivity() {
        Intent intent = new Intent(choose_place.this, choose_place_m.class);
        intent.putExtra("place1", locationEditText1.getText().toString());
        intent.putExtra("place2", locationEditText2.getText().toString());
        intent.putExtra("place3", locationEditText3.getText().toString());
        intent.putStringArrayListExtra("datetime_list", dateTimeList);
        startActivity(intent);
        finish();
    }
}
