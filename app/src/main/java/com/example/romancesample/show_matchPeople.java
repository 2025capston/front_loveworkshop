package com.example.romancesample;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class show_matchPeople extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_match_people);

        // 하단 네비게이션 설정
        setupBottomNavigation(R.id.nav_luv);  // 여기에서 'nav_luv'는 현재 선택된 메뉴 아이템입니다.

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // matchthisperson 버튼 눌렀을 때 다이얼로그 띄우기
        Button matchBtn = findViewById(R.id.matchthisperson);
        Button nExt=findViewById(R.id.nextideal);



        matchBtn.setOnClickListener(v -> showMatchDialog());
        nExt.setOnClickListener(v -> {
            Intent intent = new Intent(show_matchPeople.this, show_clones.class);
            startActivity(intent);
        });
    }


    private void showMatchDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.match_warn);

        // dialog 내부의 btnyes 버튼 클릭 시 -> check_list_of_people로 이동
        Button btnYes = dialog.findViewById(R.id.btnyes);
        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(show_matchPeople.this, check_list_of_people.class);
            startActivity(intent);
            dialog.dismiss();
        });

        // (선택) 취소 버튼이 있다면 여기에 dismiss 처리
        // Button btnNo = dialog.findViewById(R.id.btnno);
        // btnNo.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
