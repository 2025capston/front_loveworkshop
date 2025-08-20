// ScheduleProposal.java
package com.example.romancesample.api;

import java.util.List;

public class ScheduleProposal {
    private List<String> dates;
    private List<String> places;

    public ScheduleProposal(List<String> dates, List<String> places) {
        this.dates = dates;
        this.places = places;
    }

    public List<String> getDates() { return dates; }
    public List<String> getPlaces() { return places; }
}
