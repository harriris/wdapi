package com.github.harriris.wdapi.services.routes.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HslRoute(String gtfsId)  { }
