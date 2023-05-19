package com.github.harriris.wdapi.restapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Disruption(DisruptedLeg disruptedLeg, String disruptionInfoMsg) { }
