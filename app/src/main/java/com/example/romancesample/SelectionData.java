package com.example.romancesample;

import java.io.Serializable;

public class SelectionData implements Serializable {
    public int height;
    public int km;
    public int age;
    public int similarity;
    public String gender;

    public String luv;

    public int chooseage;


    public SelectionData() {
        this.height = 0;
        this.km = 0;
        this.age = 0;
        this.similarity = 0;
        this.gender = "남성"; // or "여성", "둘 다"
        this.luv="이성애자";
        this.chooseage=0;
    }
}
