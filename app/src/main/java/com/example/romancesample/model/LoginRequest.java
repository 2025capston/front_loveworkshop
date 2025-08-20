package com.example.romancesample.model;

public class LoginRequest {
    private String email;
    private String passwordHash;

    public LoginRequest(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
