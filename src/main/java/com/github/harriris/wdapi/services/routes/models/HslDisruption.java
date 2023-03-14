package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HslDisruption {
    private String alertDescriptionText;
    private Long effectiveStartDate;
    private Long effectiveEndDate;
    private HslTrip trip;
    private HslRoute route;
    public Long startTime() {
        if (this.getEffectiveStartDate() == null) {
            return null;
        }
        return this.getEffectiveStartDate() * 1000;
    }

    public Long endTime() {
        if (this.getEffectiveEndDate() == null) {
            return null;
        }
        return this.getEffectiveEndDate() * 1000;
    }

    public boolean legIsDisrupted(HslItineraryLeg hslLeg) {
        if (hslLeg.getRoute() == null || this.getRoute() == null) {
            return false;
        }
        if (this.startTime() == null || this.endTime() == null) {
            return false;
        }
        return hslLeg.getRoute().getGtfsId().equals(this.getRoute().getGtfsId())
                && (hslLeg.getStartTime() >= this.startTime() && hslLeg.getStartTime() <= this.endTime()
                    || hslLeg.getEndTime() >= this.startTime() && hslLeg.getEndTime() <= this.endTime());
    }

    public String getAlertDescriptionText() {
        return alertDescriptionText;
    }

    public void setAlertDescriptionText(String alertDescriptionText) {
        this.alertDescriptionText = alertDescriptionText;
    }

    public Long getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Long effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Long getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Long effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
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
