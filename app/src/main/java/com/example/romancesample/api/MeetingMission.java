package com.example.romancesample.api;

public class MeetingMission {
    private Long id;
    private Long matchRequestId;
    private String scheduledTime;
    private boolean triggered;

    public Long getId() { return id; }
    public Long getMatchRequestId() { return matchRequestId; }
    public String getScheduledTime() { return scheduledTime; }
    public boolean isTriggered() { return triggered; }
}
