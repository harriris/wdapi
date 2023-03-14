package com.github.harriris.wdapi.restapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Disruption {
    private DisruptedLeg disruptedLeg;
    private String disruptionInfoMsg;

    public Disruption(DisruptedLeg disruptedLeg, String disruptionInfoMsg) {
        this.disruptedLeg = disruptedLeg;
        this.disruptionInfoMsg = disruptionInfoMsg;
    }

    public DisruptedLeg getDisruptedLeg() {
        return disruptedLeg;
    }

    public void setDisruptedLeg(DisruptedLeg disruptedLeg) {
        this.disruptedLeg = disruptedLeg;
    }

    public String getDisruptionInfoMsg() {
        return disruptionInfoMsg;
    }

    public void setDisruptionInfoMsg(String disruptionInfoMsg) {
        this.disruptionInfoMsg = disruptionInfoMsg;
    }
}
