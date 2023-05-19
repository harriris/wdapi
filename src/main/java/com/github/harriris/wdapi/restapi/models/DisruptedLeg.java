package com.github.harriris.wdapi.restapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DisruptedLeg(Long startTime, Long endTime, String mode, String routeGtfsId) { }
