package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HslItineraryLeg(Long startTime, Long endTime, String mode, HslTrip trip, HslRoute route) {
    public String getStartTimeISOString() {
        return Instant.ofEpochMilli(this.startTime).toString();
    }
    public String getEndTimeISOString() {
        return Instant.ofEpochMilli(this.endTime).toString();
    }
}
