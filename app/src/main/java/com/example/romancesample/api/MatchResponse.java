package com.example.romancesample.api;

public class MatchResponse {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String status;

    // 추가 필드
    private String profileUrl;
    private String height;
    private String live;

    public Long getId() { return id; }
    public Long getFromUserId() { return fromUserId; }
    public Long getToUserId() { return toUserId; }
    public String getStatus() { return status; }

    public String getProfileUrl() { return profileUrl; }
    public String getHeight() { return height; }
    public String getLive() { return live; }
}
