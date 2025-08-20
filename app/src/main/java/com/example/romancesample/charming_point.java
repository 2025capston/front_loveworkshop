package com.example.romancesample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class charming_point extends AppCompatActivity {
    Button gallerybtn;
    ImageView imageView;
    final int GET_GALLERY_IMAGE = 200;

    SelectionData selectionData; // SelectionData 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charming_point);

        // SelectionData 받아오기
        selectionData = (SelectionData) getIntent().getSerializableExtra("selectionData");

        // 갤러리 버튼 및 이미지 뷰 초기화
        gallerybtn = findViewById(R.id.choose_ootd); // "사진 등록하기" 버튼
        imageView = findViewById(R.id.imageView3); // 선택한 이미지를 표시할 ImageView

        // 갤러리에서 이미지 선택하기
        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        // 다음 화면으로 이동
        ImageView next = findViewById(R.id.next5);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(charming_point.this, choose_clone.class);
                intent.putExtra("selectionData", selectionData); // 다음 액티비티에 데이터 전달
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageView.setImageURI(selectedImageUri);
            }
        }
    }
}
