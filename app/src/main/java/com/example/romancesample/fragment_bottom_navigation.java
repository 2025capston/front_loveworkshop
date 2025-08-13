package com.example.romancesample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class fragment_bottom_navigation extends Fragment {

    // 생성자 (필요시 사용)
    public fragment_bottom_navigation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment의 레이아웃을 반환
        return inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
    }
}
