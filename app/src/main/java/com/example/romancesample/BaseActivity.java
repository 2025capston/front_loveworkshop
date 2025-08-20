package com.example.romancesample;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNavigation(int selectedItemId) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(selectedItemId);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // 🔹 기존 메뉴 처리
            if (id == R.id.nav_luv && selectedItemId != R.id.nav_luv) {
                startActivity(new Intent(this, show_clones.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (id == R.id.nav_message && selectedItemId != R.id.nav_message) {
                startActivity(new Intent(this, check_list_of_people.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (id == R.id.nav_alarm && selectedItemId != R.id.nav_alarm) {
                // 연결된 페이지 없음
                return true;

            } else if (id == R.id.nav_profile && selectedItemId != R.id.nav_profile) {
                startActivity(new Intent(this, show_profile.class));
                overridePendingTransition(0, 0);
                return true;
            }

            // 🔹 새 메뉴 처리 (second_nav_menu.xml)
            else if (id == R.id.nav_mission && selectedItemId != R.id.nav_mission) {
                startActivity(new Intent(this, Day1.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (id == R.id.nav_promise && selectedItemId != R.id.nav_promise) {
                startActivity(new Intent(this, wait_day3.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (id == R.id.nav_alarm2 && selectedItemId != R.id.nav_alarm2) {
                // 연결된 페이지 없음
                return true;

            } else if (id == R.id.nav_profile2 && selectedItemId != R.id.nav_profile2) {
                startActivity(new Intent(this, show_profile.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}
