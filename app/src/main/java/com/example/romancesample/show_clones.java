package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class show_clones extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clones);

        // 하단 네비게이션 설정
        setupBottomNavigation(R.id.nav_luv);

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 리스트뷰 및 데이터 설정
        ListView listView = findViewById(R.id.clone_listview);

        List<CloneItem> cloneList = new ArrayList<>();
        cloneList.add(new CloneItem(R.drawable.yug, "170cm 25세", "   2km"));
        cloneList.add(new CloneItem(R.drawable.saku, "163cm 28세", "   1.5km"));
        cloneList.add(new CloneItem(R.drawable.nayeon, "159.8cm 24세", "   3km"));

        CloneAdapter adapter = new CloneAdapter(this, cloneList);
        listView.setAdapter(adapter);

        // 항목 클릭 시 show_matchPeople 액티비티로 이동
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(show_clones.this, show_matchPeople.class);
            startActivity(intent);
        });
    }
}
