package com.github.harriris.wdapi.services.routes;

import com.github.harriris.wdapi.services.routes.models.HslDisruption;
import com.github.harriris.wdapi.services.routes.models.HslItinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final HttpGraphQlClient graphQlClient;

    @Autowired
    public HslRouteApiService(@Value("${digitransit.api.key}") String apiKey,
                              @Value("${digitransit.api.url}") String apiUrl) {
        WebClient webClient = WebClient.builder().build();
        this.graphQlClient = HttpGraphQlClient.builder(webClient)
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.add("digitransit-subscription-key", apiKey);
                })
                .url(apiUrl)
                .build();
    }

    public ArrayList<HslItinerary> getItineraries(Point2D.Double startCoordinates, Point2D.Double endCoordinates) {
        String queryTemplate = """
                {
                    plan(
                        from: {lat: %s, lon: %s}
                        to: {lat: %s, lon: %s}
                        numItineraries: 10
                        transportModes: [{mode: WALK}, {mode: BUS}]
                    ) {
                        itineraries {
                            legs {
                                startTime
                                endTime
                                mode
                                trip {
                                    gtfsId
                                }
                                route {
                                    gtfsId
                                }
                            }
                        }
                    }
                }
                """;
        String query = String.format(
                queryTemplate, startCoordinates.x, startCoordinates.y, endCoordinates.x, endCoordinates.y
        );
        return new ArrayList<>(Objects.requireNonNull(this.graphQlClient.document(query)
                .retrieve("plan.itineraries")
                .toEntityList(HslItinerary.class)
                .block()));
    }

    public ArrayList<HslDisruption> getDisruptions(boolean requireRoute) {
        String query = """
                {
                    alerts(severityLevel: [WARNING, SEVERE]) {
                        alertDescriptionText
                        effectiveStartDate
                        effectiveEndDate
                        trip {
                            gtfsId
                        }
                        route {
                            gtfsId
                        }
                    }
                }
                """;
        List<HslDisruption> disruptions = this.graphQlClient.document(query)
                .retrieve("alerts")
                .toEntityList(HslDisruption.class)
                .block();
        if (disruptions == null) {
            return new ArrayList<>();
        }
        if (requireRoute) {
            return disruptions.stream()
                    .filter(hslDisruption -> hslDisruption.route() != null)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return new ArrayList<>(disruptions);
    }
}
