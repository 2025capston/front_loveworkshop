package com.example.romancesample;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;

    // 생성자
    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getter & Setter
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

