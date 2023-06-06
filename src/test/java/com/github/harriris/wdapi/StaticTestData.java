package com.github.harriris.wdapi;

import com.github.harriris.wdapi.restapi.models.DisruptedLeg;
import com.github.harriris.wdapi.restapi.models.Disruption;
import com.github.harriris.wdapi.services.routes.models.HslDisruption;
import com.github.harriris.wdapi.services.routes.models.HslItinerary;
import com.github.harriris.wdapi.services.routes.models.HslItineraryLeg;
import com.github.harriris.wdapi.services.routes.models.HslRoute;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class StaticTestData {
    public static final Point2D.Double START_POINT = new Point2D.Double(65.05403708414987, 25.46518359145660);
    public static final Point2D.Double END_POINT = new Point2D.Double(65.06192653071253, 25.43950280497612);

    public static final HslRoute ROUTE_0 = new HslRoute("OULU:666");
    public static final HslRoute ROUTE_1 = new HslRoute("OULU:999");

    public static final HslItineraryLeg HSL_LEG_0 = new HslItineraryLeg(
            1679077257000L, 1679077342000L, "WALK", null, null
    );
    public static final HslItineraryLeg HSL_LEG_1 = new HslItineraryLeg(
            1679078820000L, 1679079480000L, "BUS", null, ROUTE_0
    );
    public static final HslItineraryLeg HSL_LEG_2 = new HslItineraryLeg(
            1679078406000L, 1679078473000L, "WALK", null, null
    );
    public static final HslItineraryLeg HSL_LEG_3 = new HslItineraryLeg(
            1679079360000L, 1679080320000L, "BUS", null, ROUTE_1
    );

    public static final List<HslItineraryLeg> LEGS_0 = List.of(HSL_LEG_0, HSL_LEG_1);
    public static final List<HslItineraryLeg> LEGS_1 = List.of(HSL_LEG_2, HSL_LEG_3);

    public static final ArrayList<HslItinerary> HSL_ITINERARIES = new ArrayList<>(List.of(
            new HslItinerary(LEGS_0), new HslItinerary(LEGS_1)
    ));

    public static final ArrayList<HslDisruption> HSL_DISRUPTIONS = new ArrayList<>(List.of(
            new HslDisruption(
                    "Pysäkki A poissa käytöstä väliaikaisesti",
                    1679078820000L / 1000, 1679079480000L / 1000,
                    null, ROUTE_0
            ),
            new HslDisruption(
                    "Pysäkki B poissa käytöstä väliaikaisesti",
                    1679079360000L / 1000, 1679080320000L / 1000,
                    null, ROUTE_1
            )
    ));

    public static final ArrayList<HslDisruption> HSL_DISRUPTIONS_WITHOUT_ROUTE = new ArrayList<>(List.of(
            new HslDisruption(
                    "Pysäkki A poissa käytöstä väliaikaisesti",
                    1679077257000L, 1679077342000L,
                    null, null
            ),
            new HslDisruption(
                    "Pysäkki B poissa käytöstä väliaikaisesti",
                    1679079360000L, 1679080320000L,
                    null, ROUTE_1
            )
    ));

    public static final ArrayList<Disruption> DISRUPTIONS = new ArrayList<>(List.of(
            new Disruption(
                new DisruptedLeg(
                        HSL_LEG_1.getStartTimeISOString(), HSL_LEG_1.getEndTimeISOString(), HSL_LEG_1.mode(),
                        HSL_LEG_1.route().gtfsId()
                ), "Pysäkki A poissa käytöstä väliaikaisesti"
            ),
            new Disruption(
                    new DisruptedLeg(
                            HSL_LEG_3.getStartTimeISOString(), HSL_LEG_3.getEndTimeISOString(), HSL_LEG_3.mode(),
                            HSL_LEG_3.route().gtfsId()
                    ),"Pysäkki B poissa käytöstä väliaikaisesti"
            )
    ));
}
