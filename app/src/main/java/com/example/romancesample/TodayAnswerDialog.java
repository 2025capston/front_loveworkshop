package com.example.romancesample;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;

public class TodayAnswerDialog extends Dialog {

    private String partnerAnswer;

    public TodayAnswerDialog(Context context, String partnerAnswer) {
        super(context);
        this.partnerAnswer = partnerAnswer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_answer); // xml 연결

        TextView answerText = findViewById(R.id.today_ans);
        answerText.setText(partnerAnswer);

        AppCompatButton okButton = findViewById(R.id.ok_next);
        okButton.setOnClickListener(v -> dismiss());
    }
}