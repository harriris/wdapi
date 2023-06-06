package com.github.harriris.wdapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.harriris.wdapi.repositories.routes.HslDisruptionRepository;
import com.github.harriris.wdapi.repositories.routes.HslItineraryRepository;
import com.github.harriris.wdapi.services.routes.HslRouteApiService;
import com.github.harriris.wdapi.services.routes.models.HslDisruption;
import com.github.harriris.wdapi.services.routes.models.HslItinerary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

@SpringBootTest
public class HslRouteApiServiceTests {
    @Autowired
    private HslRouteApiService hslRouteApiService;

    @MockBean
    private HslItineraryRepository hslItineraryRepository;

    @MockBean
    private HslDisruptionRepository hslDisruptionRepository;

    @Test
    public void getItineraries() {
        when(hslItineraryRepository.findByCoordinates(any(), any())).thenReturn(StaticTestData.ITINERARIES);
        ArrayList<HslItinerary> itineraries = hslRouteApiService
                .getItineraries(StaticTestData.START_POINT, StaticTestData.END_POINT);
        assertThat(itineraries.size()).isEqualTo(StaticTestData.ITINERARIES.size());
    }

    @Test
    public void getDisruptionsRoutesNotRequired() {
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.DISRUPTIONS);
        ArrayList<HslDisruption> disruptions = hslRouteApiService.getDisruptions(false);
        assertThat(disruptions.size()).isEqualTo(StaticTestData.DISRUPTIONS.size());
    }

    @Test
    public void getDisruptionsRoutesRequired() {
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.DISRUPTIONS);
        ArrayList<HslDisruption> disruptions = hslRouteApiService.getDisruptions(true);
        assertThat(disruptions.size()).isEqualTo(StaticTestData.DISRUPTIONS.size());
    }

    @Test
    public void getDisruptionsRoutesNotRequired_RouteMissing() {
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.DISRUPTIONS_WITHOUT_ROUTE);
        ArrayList<HslDisruption> disruptions = hslRouteApiService.getDisruptions(false);
        assertThat(disruptions.size()).isEqualTo(StaticTestData.DISRUPTIONS_WITHOUT_ROUTE.size());
    }

    @Test
    public void getDisruptionsRoutesRequired_RouteMissing() {
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.DISRUPTIONS_WITHOUT_ROUTE);
        ArrayList<HslDisruption> disruptions = hslRouteApiService.getDisruptions(true);
        assertThat(disruptions.size()).isEqualTo(StaticTestData.DISRUPTIONS_WITHOUT_ROUTE.size() - 1);
    }
}
