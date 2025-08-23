package com.example.romancesample.model;

public class MeetingResultResponseDTO {
    private int id;
    private String status;
    private String meetingResult;
    private String matchKeepStatus; // null 가능

    public int getId() { return id; }
    public String getStatus() { return status; }
    public String getMeetingResult() { return meetingResult; }
    public String getMatchKeepStatus() { return matchKeepStatus; }
}
