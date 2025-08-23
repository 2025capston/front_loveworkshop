package com.example.romancesample.model;

public class MatchKeepStatusRequestDTO {
    private String matchKeepStatus;

    public MatchKeepStatusRequestDTO(String matchKeepStatus) {
        this.matchKeepStatus = matchKeepStatus;
    }

    public String getMatchKeepStatus() {
        return matchKeepStatus;
    }

    public void setMatchKeepStatus(String matchKeepStatus) {
        this.matchKeepStatus = matchKeepStatus;
    }
}
