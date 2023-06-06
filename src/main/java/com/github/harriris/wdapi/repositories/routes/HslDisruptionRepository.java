package com.github.harriris.wdapi.repositories.routes;

import com.github.harriris.wdapi.services.routes.models.HslDisruption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Objects;

@Repository
public class HslDisruptionRepository {
    private final HttpGraphQlClient graphQlClient;

    private static final String FIND_ALL_QUERY = """
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

    @Autowired
    public HslDisruptionRepository(@Value("${digitransit.api.key}") String apiKey,
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

    public ArrayList<HslDisruption> findAll() {
        return new ArrayList<>(Objects.requireNonNull(this.graphQlClient.document(FIND_ALL_QUERY)
                .retrieve("alerts")
                .toEntityList(HslDisruption.class)
                .block()));
    }
}
