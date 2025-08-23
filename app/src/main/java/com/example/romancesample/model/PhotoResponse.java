package com.example.romancesample.model;

public class PhotoResponse {
    private int id;
    private int userId;
    private String photoUrl;
    private boolean isProfile;
    private String createdAt;
    private String updatedAt;

    // 기본 생성자 (Retrofit 역직렬화용)
    public PhotoResponse() {}

    // 전체 필드 생성자
    public PhotoResponse(int id, int userId, String photoUrl, boolean isProfile, String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.photoUrl = photoUrl;
        this.isProfile = isProfile;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isProfile() {
        return isProfile;
    }

    public void setProfile(boolean profile) {
        isProfile = profile;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
