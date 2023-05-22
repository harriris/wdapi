package com.github.harriris.wdapi.restapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DisruptedLeg(String startTime, String endTime, String mode, String routeGtfsId) { }
