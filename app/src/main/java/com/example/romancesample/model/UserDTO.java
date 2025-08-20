package com.example.romancesample.model;

public class UserDTO {
    private String email;
    private String name;
    private String passwordHash;
    private String phone;

    public UserDTO(String email, String name, String passwordHash, String phone) {
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.phone = phone;
    }

    // Getter & Setter 생략 가능 (필요 시 추가)
}
