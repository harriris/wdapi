package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HslRoute {
    private String gtfsId;

    public HslRoute() {}

    public HslRoute(String gtfsId) {
        this.gtfsId = gtfsId;
    }

    public void setGtfsId(String gtfsId) {
        this.gtfsId = gtfsId;
    }

    public String getGtfsId() {
        return gtfsId;
    }
}
