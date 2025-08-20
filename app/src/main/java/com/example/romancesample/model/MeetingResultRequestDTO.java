package com.example.romancesample.model;

public class MeetingResultRequestDTO {
    private String meetingResult;

    public MeetingResultRequestDTO(String meetingResult) {
        this.meetingResult = meetingResult;
    }

    public String getMeetingResult() {
        return meetingResult;
    }

    public void setMeetingResult(String meetingResult) {
        this.meetingResult = meetingResult;
    }
}
