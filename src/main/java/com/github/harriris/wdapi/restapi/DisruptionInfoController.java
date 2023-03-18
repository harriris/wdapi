package com.github.harriris.wdapi.restapi;

import com.github.harriris.wdapi.restapi.models.Disruption;
import com.github.harriris.wdapi.restapi.models.DisruptedLeg;
import com.github.harriris.wdapi.services.routes.models.HslDisruption;
import com.github.harriris.wdapi.services.routes.models.HslItinerary;
import com.github.harriris.wdapi.services.routes.models.HslItineraryLeg;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class DisruptionInfoController {
    private final HslRouteApiService hslRouteApiService;

    public DisruptionInfoController(HslRouteApiService hslRouteApiService) {
        this.hslRouteApiService = hslRouteApiService;
    }

    @Validated
    @GetMapping("/itineraries")
    public ArrayList<HslItinerary> itineraries(@RequestParam double sLat,
                                               @RequestParam double sLon,
                                               @RequestParam double eLat,
                                               @RequestParam double eLon) {
        ArrayList<HslItinerary> hslItineraries = hslRouteApiService
                .getItineraries(new Point2D.Double(sLat, sLon), new Point2D.Double(eLat, eLon));
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
        ArrayList<HslItinerary> hslItineraries = this.itineraries(sLat, sLon, eLat, eLon);
        ArrayList<HslDisruption> hslDisruptions = hslRouteApiService.getDisruptions(true);
        return this.getAffectingDisruptions(hslItineraries, hslDisruptions);
    }

    private ArrayList<Disruption> getAffectingDisruptions(ArrayList<HslItinerary> hslItineraries,
                                                          ArrayList<HslDisruption> hslDisruptions) {
        ArrayList<Disruption> affectingDisruptions = new ArrayList<>();

        ArrayList<HslItineraryLeg> legs = hslItineraries.stream()
                .flatMap(hslItinerary -> hslItinerary.getLegs().stream())
                .collect(Collectors.toCollection(ArrayList::new));

        legs.forEach(hslLeg -> hslDisruptions.forEach(hslDisruption -> {
            if (hslDisruption.legIsDisrupted(hslLeg)) {
                final DisruptedLeg disruptedLeg = new DisruptedLeg(
                        hslLeg.getStartTime(), hslLeg.getEndTime(), hslLeg.getMode(), hslLeg.getRoute().getGtfsId()
                );
                final Disruption disruption = new Disruption(disruptedLeg, hslDisruption.getAlertDescriptionText());
                affectingDisruptions.add(disruption);
            }
        }));

        return affectingDisruptions;
    }
}
