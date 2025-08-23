package com.example.romancesample.model;

public class ReportRequest {
    private int requestId;
    private int reporterUserId;
    private String reason;
    private String details;

    public ReportRequest(int requestId, int reporterUserId, String reason, String details) {
        this.requestId = requestId;
        this.reporterUserId = reporterUserId;
        this.reason = reason;
        this.details = details;
    }

    // getter, setter 생략 가능
}
