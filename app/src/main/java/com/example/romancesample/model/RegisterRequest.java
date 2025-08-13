package com.example.romancesample.model;

public class RegisterRequest {
    private String name;
    private String email;
    private String passwordHash;

    public RegisterRequest(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getter/Setter 생략 가능 (Lombok 쓰지 않는 경우 수동으로 추가)
}
