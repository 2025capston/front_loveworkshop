package com.example.romancesample;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class dayN_mission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_day_nmission);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 랜덤 질문 표시
        TextView missionText = findViewById(R.id.landom_mission);
        String[] questions = {
                "오늘 하루 어땠나요?",
                "재미있는 질문 1개 작성",
                "내가 피하고 싶은 만남 유형 체크",
                "친한 친구가 보는 나의 장점 적기",
                "오늘의 TMI(쓸데없는 정보) 작성하기",
                "자기소개 글 5문장 이상 작성하기",
                "내 MBTI 공개하기",
                "내가 좋아하는 것 3개 적기",
                "내가 사는 동네 소개 1줄 적기",
                "나의 평소 스타일 태그 3개 선택",

                "오늘 기분을 이모지로 표현하기",
                "내 인생 영화 or 드라마 공유",
                "나를 표현하는 3가지 단어 적기",
                "\"지금 듣고 있는 노래\" 공유",
                "내가 최근에 웃었던 이유 적기",
                "하루 동안 했던 행동 중 자랑하고 싶은 거 하나",
                "오늘 먹은 음식 사진 업로드",
                "가장 최근 찍은 셀카 올리기",
                "나의 소소한 루틴 소개",
                "\"내가 가장 잘하는 것\" 한 줄 소개",

                "친구가 나에게 자주 하는 말 적기",
                "오늘의 패션 포인트 공유",
                "나만의 스트레스 해소법 공유",
                "요즘 빠져있는 취미 공유",
                "내가 가장 좋아하는 계절 + 이유",
                "최근 다녀온 장소 사진 올리기",
                "‘오늘 하루 나에게 점수 주기’ (10점 만점)",
                "지금 당장 하고 싶은 말 한 줄 남기기",
                "나와 닮은 연예인 or 캐릭터 말해보기",
                "오늘 있었던 웃긴 일 공유",

                "지금 가장 먹고 싶은 음식은?",
                "나의 최애 간식 or 음료 추천",
                "친구에게 하고 싶은 말 한 줄 쓰기",
                "과거 나에게 조언 한 마디 해준다면?",
                "오늘 하늘 사진 찍어서 올리기",
                "휴대폰 갤러리 속 랜덤 사진 하나 공유",
                "최근 검색한 단어 or 주제 공개",
                "가장 기억에 남는 여행지 이야기",
                "내가 좋아하는 향 또는 향수 소개",
                "어릴 적 꿈은 무엇이었나요?",
                "지금 내 책상 위에 있는 물건 3개",
                "나만 아는 꿀팁 or 꿀조합 공유",
                "내가 자주 쓰는 이모지 3개",
                "오늘 하루를 색깔로 표현한다면?",
                "최근 감동받았던 순간 공유",
                "나만의 '힐링 플레이리스트' 공유",
                "내가 자주 가는 장소 or 단골 가게 소개",
                "내가 가장 좋아하는 요일과 이유",
                "\"내가 제일 잘 나갈 때는 언제였을까?\"",
                "마지막으로 울었던 이유 기억나나요?"
        };


        Random random = new Random();
        int index = random.nextInt(questions.length);
        missionText.setText(questions[index]);
    }
}
