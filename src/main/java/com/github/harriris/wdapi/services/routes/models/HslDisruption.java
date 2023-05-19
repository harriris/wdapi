package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HslDisruption(String alertDescriptionText, Long effectiveStartDate, Long effectiveEndDate,
                            HslTrip trip, HslRoute route) {
    
    public Long startTime() {
        if (this.effectiveStartDate == null) {
            return null;
        }
        return this.effectiveStartDate * 1000;
    }

    public Long endTime() {
        if (this.effectiveEndDate == null) {
            return null;
        }
        return this.effectiveEndDate * 1000;
    }

    public boolean legIsDisrupted(HslItineraryLeg hslLeg) {
        if (hslLeg.route() == null || this.route == null) {
            return false;
        }
        if (this.startTime() == null || this.endTime() == null) {
            return false;
        }
        return hslLeg.route().gtfsId().equals(this.route.gtfsId())
                && (hslLeg.startTime() >= this.startTime() && hslLeg.startTime() <= this.endTime()
                    || hslLeg.endTime() >= this.startTime() && hslLeg.endTime() <= this.endTime());
    }
}
