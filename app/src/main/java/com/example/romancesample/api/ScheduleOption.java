package com.example.romancesample.api;

import java.io.Serializable;

public class ScheduleOption implements Serializable {
    private Long dateOptionId;
    private Long timeOptionId;
    private Long placeOptionId;
    private String displayTime;   // 예: "8월 20일 15:00~16:00"
    private String displayPlace;  // 예: "카페 ABC"

    public ScheduleOption(Long dateOptionId, Long timeOptionId, Long placeOptionId,
                          String displayTime, String displayPlace) {
        this.dateOptionId = dateOptionId;
        this.timeOptionId = timeOptionId;
        this.placeOptionId = placeOptionId;
        this.displayTime = displayTime;
        this.displayPlace = displayPlace;
    }

    public Long getDateOptionId() { return dateOptionId; }
    public Long getTimeOptionId() { return timeOptionId; }
    public Long getPlaceOptionId() { return placeOptionId; }
    public String getDisplayTime() { return displayTime; }
    public String getDisplayPlace() { return displayPlace; }
}
