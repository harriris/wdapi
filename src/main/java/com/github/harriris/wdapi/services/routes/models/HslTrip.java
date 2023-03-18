package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HslTrip {
    private String gtfsId;

    public HslTrip() {}

    public HslTrip(String gtfsId) {
        this.gtfsId = gtfsId;
    }
    
    public void setGtfsId(String gtfsId) {
        this.gtfsId = gtfsId;
    }

    public String getGtfsId() {
        return this.gtfsId;
    }
}
