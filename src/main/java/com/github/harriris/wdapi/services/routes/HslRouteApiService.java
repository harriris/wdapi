package com.github.harriris.wdapi.services.routes;

import com.github.harriris.wdapi.repositories.routes.HslDisruptionRepository;
import com.github.harriris.wdapi.repositories.routes.HslItineraryRepository;
import com.github.harriris.wdapi.restapi.models.DisruptedLeg;
import com.github.harriris.wdapi.restapi.models.Disruption;
import com.github.harriris.wdapi.services.routes.models.HslDisruption;
import com.github.harriris.wdapi.services.routes.models.HslItinerary;
import com.github.harriris.wdapi.services.routes.models.HslItineraryLeg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class HslRouteApiService {
    @Autowired
    private HslItineraryRepository hslItineraryRepository;

    @Autowired
    private HslDisruptionRepository hslDisruptionRepository;

    public ArrayList<HslItinerary> getItineraries(Point2D.Double startCoordinates, Point2D.Double endCoordinates) {
        return this.hslItineraryRepository.findByCoordinates(startCoordinates, endCoordinates);
    }

    public ArrayList<HslDisruption> getDisruptions(boolean requireRoute) {
        ArrayList<HslDisruption> disruptions = this.hslDisruptionRepository.findAll();
        if (requireRoute) {
            return disruptions.stream()
                    .filter(hslDisruption -> hslDisruption.route() != null)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return disruptions;
    }

    public ArrayList<Disruption> getAffectingDisruptions(Point2D.Double startCoordinates, Point2D.Double endCoordinates) {
        ArrayList<HslItinerary> hslItineraries = this.getItineraries(startCoordinates, endCoordinates);
        ArrayList<HslDisruption> hslDisruptions = this.getDisruptions(true);

        ArrayList<Disruption> affectingDisruptions = new ArrayList<>();

        ArrayList<HslItineraryLeg> legs = hslItineraries.stream()
                .flatMap(hslItinerary -> hslItinerary.legs().stream())
                .collect(Collectors.toCollection(ArrayList::new));

        legs.forEach(hslLeg -> hslDisruptions.forEach(hslDisruption -> {
            if (hslDisruption.legIsDisrupted(hslLeg)) {
                final DisruptedLeg disruptedLeg = new DisruptedLeg(
                        hslLeg.getStartTimeISOString(), hslLeg.getEndTimeISOString(), hslLeg.mode(),
                        hslLeg.route().gtfsId()
                );
                final Disruption disruption = new Disruption(disruptedLeg, hslDisruption.alertDescriptionText());
                affectingDisruptions.add(disruption);
            }
        }));

        return affectingDisruptions;
    }
}
