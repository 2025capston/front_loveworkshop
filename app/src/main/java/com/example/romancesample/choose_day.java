package com.example.romancesample;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class choose_day extends AppCompatActivity {

    CalendarView calendarView;
    LinearLayout dateTimeContainer;
    String selectedDate = "";
    ArrayList<String> dateTimeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_day);

        calendarView = findViewById(R.id.calendarView);
        dateTimeContainer = findViewById(R.id.dateTimeContainer);

        // 오늘 날짜부터 7일 후까지 선택 가능
        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        long sevenDaysLater = calendar.getTimeInMillis();
        calendarView.setMinDate(today);
        calendarView.setMaxDate(sevenDaysLater);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = (month + 1) + "월 " + dayOfMonth + "일";
            showTimePickers(); // 날짜 선택 시 바로 시간 선택
        });

        findViewById(R.id.gotoplace).setOnClickListener(v -> {
            Intent intent = new Intent(choose_day.this, choose_place.class);
            intent.putStringArrayListExtra("datetime_list", dateTimeList);
            startActivity(intent);
        });
    }

    private void showTimePickers() {
        TimePickerDialog startTimeDialog = new TimePickerDialog(this, (view, startHour, startMinute) -> {
            TimePickerDialog endTimeDialog = new TimePickerDialog(this, (tp, endHour, endMinute) -> {
                // 날짜+시간 세트로 합치기
                String timeRange = String.format("%02d:%02d~%02d:%02d", startHour, startMinute, endHour, endMinute);
                String dateTimeSet = selectedDate + " " + timeRange;
                dateTimeList.add(dateTimeSet);

                // 화면에 추가
                TextView newText = new TextView(this);
                newText.setText(dateTimeSet);
                newText.setTextSize(18);
                newText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                newText.setPadding(8, 8, 8, 8);
                newText.setBackgroundResource(R.drawable.calender_src);
                newText.setTypeface(ResourcesCompat.getFont(this, R.font.scd5));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) (getResources().getDisplayMetrics().density * 350),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.topMargin = (int) (getResources().getDisplayMetrics().density * 10);
                newText.setLayoutParams(params);

                dateTimeContainer.addView(newText);

            }, 16, 0, true);
            endTimeDialog.show();
        }, 15, 0, true);
        startTimeDialog.show();
    }
}
