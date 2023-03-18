package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HslItineraryLeg {
    private Long startTime;
    private Long endTime;
    private String mode;
    private HslTrip trip;
    private HslRoute route;

    public HslItineraryLeg() {}

    public HslItineraryLeg(Long startTime, Long endTime, String mode, HslTrip trip, HslRoute route) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.trip = trip;
        this.route = route;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public HslTrip getTrip() {
        return trip;
    }

    public void setTrip(HslTrip trip) {
        this.trip = trip;
    }

    public HslRoute getRoute() {
        return route;
    }

    public void setRoute(HslRoute route) {
        this.route = route;
    }
}
