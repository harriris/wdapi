package com.github.harriris.wdapi.restapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DisruptedLeg {
    private Long startTime;
    private Long endTime;
    private String mode;
    private String routeGtfsId;

    public DisruptedLeg(Long startTime, Long endTime, String mode, String routeGtfsId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.mode = mode;
        this.routeGtfsId = routeGtfsId;
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

    public String getRouteGtfsId() {
        return routeGtfsId;
    }

    public void setRouteGtfsId(String routeGtfsId) {
        this.routeGtfsId = routeGtfsId;
    }
}
