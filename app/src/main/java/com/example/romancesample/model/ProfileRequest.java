package com.example.romancesample.model;

public class ProfileRequest {
    private int birthYear;
    private int height;
    private String residence;
    private String sexualOrientation;

    public ProfileRequest(int birthYear, int height, String residence, String sexualOrientation) {
        this.birthYear = birthYear;
        this.height = height;
        this.residence = residence;
        this.sexualOrientation = sexualOrientation;
    }

    // Getter/Setter 생략 가능
}