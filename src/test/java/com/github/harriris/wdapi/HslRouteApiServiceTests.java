package com.github.harriris.wdapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.harriris.wdapi.repositories.routes.HslDisruptionRepository;
import com.github.harriris.wdapi.repositories.routes.HslItineraryRepository;
import com.github.harriris.wdapi.restapi.models.Disruption;
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
    public void getItineraries_Success() {
        when(hslItineraryRepository.findByCoordinates(any(), any())).thenReturn(StaticTestData.HSL_ITINERARIES);
        ArrayList<HslItinerary> itineraries = hslRouteApiService
                .getItineraries(StaticTestData.START_POINT, StaticTestData.END_POINT);
        assertThat(itineraries.size()).isEqualTo(StaticTestData.HSL_ITINERARIES.size());
    }

    @Test
    public void getDisruptionsRoutesNotRequired_Success() {
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.HSL_DISRUPTIONS);
        ArrayList<HslDisruption> disruptions = hslRouteApiService.getDisruptions(false);
        assertThat(disruptions.size()).isEqualTo(StaticTestData.HSL_DISRUPTIONS.size());
    }

    @Test
    public void getDisruptionsRoutesRequired_Success() {
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.HSL_DISRUPTIONS);
        ArrayList<HslDisruption> disruptions = hslRouteApiService.getDisruptions(true);
        assertThat(disruptions.size()).isEqualTo(StaticTestData.HSL_DISRUPTIONS.size());
    }

    @Test
    public void getDisruptionsRoutesNotRequired_RouteMissing() {
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.HSL_DISRUPTIONS_WITHOUT_ROUTE);
        ArrayList<HslDisruption> disruptions = hslRouteApiService.getDisruptions(false);
        assertThat(disruptions.size()).isEqualTo(StaticTestData.HSL_DISRUPTIONS_WITHOUT_ROUTE.size());
    }

    @Test
    public void getDisruptionsRoutesRequired_RouteMissing() {
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.HSL_DISRUPTIONS_WITHOUT_ROUTE);
        ArrayList<HslDisruption> disruptions = hslRouteApiService.getDisruptions(true);
        assertThat(disruptions.size()).isEqualTo(StaticTestData.HSL_DISRUPTIONS_WITHOUT_ROUTE.size() - 1);
    }

    @Test
    public void getAffectingDisruptions_Success() {
        when(hslItineraryRepository.findByCoordinates(any(), any())).thenReturn(StaticTestData.HSL_ITINERARIES);
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.HSL_DISRUPTIONS);
        ArrayList<Disruption> disruptions = hslRouteApiService.getAffectingDisruptions(
                StaticTestData.START_POINT, StaticTestData.END_POINT
        );
        assertThat(disruptions.size()).isEqualTo(StaticTestData.HSL_DISRUPTIONS.size());
    }

    @Test
    public void getAffectingDisruptions_ItinerariesNotFound() {
        when(hslItineraryRepository.findByCoordinates(any(), any())).thenReturn(new ArrayList<>());
        when(hslDisruptionRepository.findAll()).thenReturn(StaticTestData.HSL_DISRUPTIONS);
        ArrayList<Disruption> disruptions = hslRouteApiService.getAffectingDisruptions(
                StaticTestData.START_POINT, StaticTestData.END_POINT
        );
        assertThat(disruptions.size()).isEqualTo(0);
    }

    @Test
    public void getAffectingDisruptions_DisruptionsNotFound() {
        when(hslItineraryRepository.findByCoordinates(any(), any())).thenReturn(StaticTestData.HSL_ITINERARIES);
        when(hslDisruptionRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<Disruption> disruptions = hslRouteApiService.getAffectingDisruptions(
                StaticTestData.START_POINT, StaticTestData.END_POINT
        );
        assertThat(disruptions.size()).isEqualTo(0);
    }
}
