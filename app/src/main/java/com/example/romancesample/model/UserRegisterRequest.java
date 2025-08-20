package com.example.romancesample.model;

public class UserRegisterRequest {
    private String email;
    private String name;
    private String password;
    private String phone;

    public UserRegisterRequest(String email, String name, String password, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }
}
