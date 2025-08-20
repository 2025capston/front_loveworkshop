package com.example.romancesample.api;

public class MissionResponse {
    private Long id;
    private Long matchMissionId;
    private Long userId;
    private String answer;
    private String submittedAt;

    public Long getId() { return id; }
    public Long getMatchMissionId() { return matchMissionId; }
    public Long getUserId() { return userId; }
    public String getAnswer() { return answer; }
    public String getSubmittedAt() { return submittedAt; }
}
