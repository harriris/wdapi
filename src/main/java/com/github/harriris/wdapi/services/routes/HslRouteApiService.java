package com.github.harriris.wdapi.services.routes;

import com.github.harriris.wdapi.services.routes.models.HslDisruption;
import com.github.harriris.wdapi.services.routes.models.HslItinerary;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HslRouteApiService {
    private static final String API_BASE_URL = "https://api.digitransit.fi/routing/v1/routers/waltti/index/graphql";

    private final HttpGraphQlClient graphQlClient;

    public HslRouteApiService() {
        WebClient webClient = WebClient.builder().build();
        this.graphQlClient = HttpGraphQlClient.builder(webClient)
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
                .url(API_BASE_URL)
                .build();
    }

    public ArrayList<HslItinerary> getItineraries(Point2D.Double startCoordinates, Point2D.Double endCoordinates) {
        String queryTemplate = "{\n" +
                               "    plan(\n" +
                               "        from: {lat: %s, lon: %s}\n" +
                               "        to: {lat: %s, lon: %s}\n" +
                               "        numItineraries: 10\n" +
                               "        transportModes: [{mode: WALK}, {mode: BUS}]\n" +
                               "    ) {\n" +
                               "        itineraries {\n" +
                               "            legs {\n" +
                               "                startTime\n" +
                               "                endTime\n" +
                               "                mode\n" +
                               "                trip {\n" +
                               "                    gtfsId\n" +
                               "                }\n" +
                               "                route {\n" +
                               "                    gtfsId\n" +
                               "                }\n" +
                               "            }\n" +
                               "        }\n" +
                               "    }\n" +
                               "}\n";
        String query = String.format(
                queryTemplate, startCoordinates.x, startCoordinates.y, endCoordinates.x, endCoordinates.y
        );
        return new ArrayList<>(Objects.requireNonNull(this.graphQlClient.document(query)
                .retrieve("plan.itineraries")
                .toEntityList(HslItinerary.class)
                .block()));
    }

    public ArrayList<HslDisruption> getDisruptions(boolean requireRoute) {
        String query = "{\n" +
                       "    alerts(severityLevel: [WARNING, SEVERE]) {\n" +
                       "        alertDescriptionText\n" +
                       "        effectiveStartDate\n" +
                       "        effectiveEndDate\n" +
                       "        trip {\n" +
                       "            gtfsId\n" +
                       "        }\n" +
                       "        route {\n" +
                       "            gtfsId\n" +
                       "        }\n" +
                       "    }\n" +
                       "}\n";
        List<HslDisruption> disruptions = this.graphQlClient.document(query)
                .retrieve("alerts")
                .toEntityList(HslDisruption.class)
                .block();
        if (disruptions == null) {
            return new ArrayList<>();
        }
        if (requireRoute) {
            return disruptions.stream()
                    .filter(hslDisruption -> hslDisruption.getRoute() != null)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return new ArrayList<>(disruptions);
    }
}
