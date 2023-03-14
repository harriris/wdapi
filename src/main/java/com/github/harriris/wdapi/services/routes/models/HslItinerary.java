package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HslItinerary {
    private List<HslItineraryLeg> legs;

    public List<HslItineraryLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<HslItineraryLeg> legs) {
        this.legs = legs;
    }
}
