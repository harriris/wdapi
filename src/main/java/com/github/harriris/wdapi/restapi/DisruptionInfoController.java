package com.github.harriris.wdapi.restapi;

import com.github.harriris.wdapi.restapi.models.Disruption;
import com.github.harriris.wdapi.services.routes.models.HslItinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import com.github.harriris.wdapi.services.routes.HslRouteApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.awt.geom.Point2D;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class DisruptionInfoController {
    @Autowired
    private HslRouteApiService hslRouteApiService;

    @Validated
    @GetMapping("/itineraries")
    public ArrayList<HslItinerary> itineraries(@RequestParam double sLat,
                                               @RequestParam double sLon,
                                               @RequestParam double eLat,
                                               @RequestParam double eLon) {
        ArrayList<HslItinerary> hslItineraries = hslRouteApiService.getItineraries(
                new Point2D.Double(sLat, sLon), new Point2D.Double(eLat, eLon)
        );
        if (hslItineraries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No itineraries found");
        }
        return hslItineraries;
    }

    @Validated
    @GetMapping("/disruptions")
    public ArrayList<Disruption> disruptions(@RequestParam double sLat,
                                             @RequestParam double sLon,
                                             @RequestParam double eLat,
                                             @RequestParam double eLon) {
        ArrayList<Disruption> affectingDisruptions = hslRouteApiService.getAffectingDisruptions(
                new Point2D.Double(sLat, sLon), new Point2D.Double(eLat, eLon)
        );
        if (affectingDisruptions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No disruptions found");
        }
        return affectingDisruptions;
    }
}
