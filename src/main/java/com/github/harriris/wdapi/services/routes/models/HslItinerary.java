package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HslItinerary(List<HslItineraryLeg> legs) { }
