package com.example.romancesample.api;

public class DailyMission {
    private Long id;
    private String content;

    public DailyMission(String content) {
        this.content = content;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
}
