package com.example.romancesample;

//import com.example.romancesample.BuildConfig;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import android.os.Bundle;

import android.util.Log;
import com.kakao.vectormap.KakaoMapSdk;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.KakaoMap;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private AutoCompleteTextView autoCompleteTextView;
    private Geocoder geocoder;
    private String fieldId;
    private List<Address> searchResultAddresses = new ArrayList<>();
    private KakaoMap kakaoMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_map);

        KakaoMapSdk.init(this, BuildConfig.KAKAO_MAP_KEY);

        mapView = findViewById(R.id.map_view);

        mapView.start(new MapLifeCycleCallback() {
            @Override
            public void onMapDestroy() {
                Log.d("KakaoMap", "onMapDestroy");
            }

            @Override
            public void onMapError(Exception e) {
                Log.e("KakaoMap", "onMapError", e);
            }
        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(KakaoMap kakaomap) {
                kakaoMap = kakaomap;
                // 지도 준비 완료 시 처리할 작업 추가 가능
            }
        });


//        autoCompleteTextView = findViewById(R.id.search_location);
//        geocoder = new Geocoder(this, Locale.KOREA);
//        fieldId = getIntent().getStringExtra("field_id");
//
//        autoCompleteTextView.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
//                searchLocation();
//                return true;
//            }
//            return false;
//        });
    }

//    private void searchLocation() {
//        String query = autoCompleteTextView.getText().toString();
//        if (query.isEmpty()) return;
//
//        try {
//            searchResultAddresses = geocoder.getFromLocationName(query, 10);
//            List<String> suggestionNames = new ArrayList<>();
//
//            for (Address address : searchResultAddresses) {
//                String name = address.getFeatureName();
//                if (name != null && !suggestionNames.contains(name)) {
//                    suggestionNames.add(name);
//                }
//            }
//
//            if (suggestionNames.isEmpty()) {
//                showAlert("검색 결과가 없습니다.");
//                return;
//            }
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("장소 선택");
//            builder.setItems(suggestionNames.toArray(new String[0]), (dialog, which) -> {
//                Address selectedAddress = searchResultAddresses.get(which);
//                GeoPoint point = new GeoPoint(selectedAddress.getLatitude(), selectedAddress.getLongitude());
//                String displayName = suggestionNames.get(which);
//                addMarkerAndReturn(point, displayName);
//            });
//            builder.setNegativeButton("취소", null);
//            builder.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            showAlert("검색 중 오류가 발생했습니다.");
//        }
//    }

//    private void addMarkerAndReturn(GeoPoint point, String placeName) {
//        Marker marker = new Marker(mapView);
//        marker.setPosition(point);
//        marker.setTitle(placeName);
//        mapView.getOverlays().clear();
//        mapView.getOverlays().add(marker);
//        mapView.invalidate();
//
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra("selected_location", placeName);
//        resultIntent.putExtra("field_id", fieldId);
//        setResult(RESULT_OK, resultIntent);
//        finish();
//    }

    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(message)
                .setPositiveButton("확인", null)
                .show();
    }
}

