package com.github.harriris.wdapi;

import com.github.harriris.wdapi.restapi.DisruptionInfoController;
import com.github.harriris.wdapi.services.routes.HslRouteApiService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DisruptionInfoController.class)
public class RestApiTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HslRouteApiService hslRouteApiService;

    private static final String ITINERARIES_URL = String.format(
            "/api/v1/itineraries?sLat=%s&sLon=%s&eLat=%s&eLon=%s",
            StaticTestData.START_POINT.x, StaticTestData.START_POINT.y,
            StaticTestData.END_POINT.x, StaticTestData.END_POINT.y
    );
    private static final String DISRUPTIONS_URL = String.format(
            "/api/v1/disruptions?sLat=%s&sLon=%s&eLat=%s&eLon=%s",
            StaticTestData.START_POINT.x, StaticTestData.START_POINT.y,
            StaticTestData.END_POINT.x, StaticTestData.END_POINT.y
    );

    @Test
    public void itinerariesMissingAllParams() throws Exception {
        this.mockMvc.perform(get("/api/v1/itineraries"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void itinerariesMissingPartialParams() throws Exception {
        final String partialParamsUrl = String.format(
                "/api/v1/itineraries?sLon=%s&eLat=%s", StaticTestData.START_POINT.y, StaticTestData.END_POINT.x
        );
        this.mockMvc.perform(get(partialParamsUrl)).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void disruptionsMissingAllParams() throws Exception {
        this.mockMvc.perform(get("/api/v1/disruptions"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void disruptionsMissingPartialParams() throws Exception {
        final String partialParamsUrl = String.format(
                "/api/v1/disruptions?sLon=%s&eLat=%s", StaticTestData.START_POINT.y, StaticTestData.END_POINT.x
        );
        this.mockMvc.perform(get(partialParamsUrl)).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void itinerariesNotFound() throws Exception {
        when(this.hslRouteApiService.getItineraries(any(), any())).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get(ITINERARIES_URL).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void itinerariesFound() throws Exception {
        when(this.hslRouteApiService.getItineraries(any(), any())).thenReturn(StaticTestData.HSL_ITINERARIES);
        this.mockMvc.perform(get(ITINERARIES_URL).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void disruptionsNotFound() throws Exception {
        when(this.hslRouteApiService.getAffectingDisruptions(any(), any())).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get(DISRUPTIONS_URL).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void disruptionsFound() throws Exception {
        when(this.hslRouteApiService.getAffectingDisruptions(any(), any())).thenReturn(StaticTestData.DISRUPTIONS);
        this.mockMvc.perform(get(DISRUPTIONS_URL).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
