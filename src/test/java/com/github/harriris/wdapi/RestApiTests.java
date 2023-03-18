package com.github.harriris.wdapi;

import com.github.harriris.wdapi.restapi.DisruptionInfoController;
import com.github.harriris.wdapi.services.routes.HslRouteApiService;
import com.github.harriris.wdapi.services.routes.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

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

    private static final Point2D.Double START_POINT = new Point2D.Double(65.05403708414987, 25.46518359145660);
    private static final Point2D.Double END_POINT = new Point2D.Double(65.06192653071253, 25.43950280497612);

    private static final HslRoute ROUTE_0 = new HslRoute("OULU:666");
    private static final HslRoute ROUTE_1 = new HslRoute("OULU:999");
    private static final List<HslItineraryLeg> LEGS_0 = List.of(
            new HslItineraryLeg(1679077257000L, 1679077342000L, "WALK", null, null),
            new HslItineraryLeg(1679078820000L, 1679079480000L, "BUS", null, ROUTE_0)
    );
    private static final List<HslItineraryLeg> LEGS_1 = List.of(
            new HslItineraryLeg(1679078406000L, 1679078473000L, "WALK", null, null),
            new HslItineraryLeg(1679079360000L, 1679080320000L, "BUS", null, ROUTE_1)
    );
    private static final ArrayList<HslItinerary> ITINERARIES = new ArrayList<>(List.of(
            new HslItinerary(LEGS_0), new HslItinerary(LEGS_1)
    ));
    private static final ArrayList<HslDisruption> DISRUPTIONS = new ArrayList<>(List.of(
            new HslDisruption(
                    "Pysäkki A poissa käytöstä väliaikaisesti",
                    1679077257000L, 1679077342000L,
                    null, ROUTE_0
            ),
            new HslDisruption(
                    "Pysäkki B poissa käytöstä väliaikaisesti",
                    1679079360000L, 1679080320000L,
                    null, ROUTE_1
            )
    ));
    private static final String ITINERARIES_URL = String.format("/api/v1/itineraries?sLat=%s&sLon=%s&eLat=%s&eLon=%s",
                                                                START_POINT.x, START_POINT.y, END_POINT.x, END_POINT.y);
    private static final String DISRUPTIONS_URL = String.format("/api/v1/disruptions?sLat=%s&sLon=%s&eLat=%s&eLon=%s",
                                                                START_POINT.x, START_POINT.y, END_POINT.x, END_POINT.y);

    @Test
    public void itinerariesMissingAllParams() throws Exception {
        this.mockMvc.perform(get("/api/v1/itineraries"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void itinerariesMissingPartialParams() throws Exception {
        final String partialParamsUrl = String.format("/api/v1/itineraries?sLon=%s&eLat=%s", START_POINT.y, END_POINT.x);
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
        when(this.hslRouteApiService.getItineraries(any(), any())).thenReturn(ITINERARIES);
        this.mockMvc.perform(get(ITINERARIES_URL).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void disruptionsNotFound() throws Exception {
        when(this.hslRouteApiService.getItineraries(any(), any())).thenReturn(new ArrayList<>());
        when(this.hslRouteApiService.getDisruptions(anyBoolean())).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get(DISRUPTIONS_URL).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void disruptionsFound() throws Exception {
        when(this.hslRouteApiService.getItineraries(any(), any())).thenReturn(ITINERARIES);
        when(this.hslRouteApiService.getDisruptions(anyBoolean())).thenReturn(DISRUPTIONS);
        this.mockMvc.perform(get(DISRUPTIONS_URL).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void disruptionsWithoutItineraries() throws Exception {
        when(this.hslRouteApiService.getItineraries(any(), any())).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get(DISRUPTIONS_URL).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
}
