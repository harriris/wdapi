package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HslItineraryLeg(Long startTime, Long endTime, String mode, HslTrip trip, HslRoute route) { }
