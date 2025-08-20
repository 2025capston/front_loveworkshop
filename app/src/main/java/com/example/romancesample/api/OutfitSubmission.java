package com.example.romancesample.api;

public class OutfitSubmission {
    private Long id;
    private Long matchRequestId;
    private Long userId;
    private String imageUrl;
    private String submissionDate;
    private String submittedAt;

    public Long getId() { return id; }
    public Long getMatchRequestId() { return matchRequestId; }
    public Long getUserId() { return userId; }
    public String getImageUrl() { return imageUrl; }
    public String getSubmissionDate() { return submissionDate; }
    public String getSubmittedAt() { return submittedAt; }
}
