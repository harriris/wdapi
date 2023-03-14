# wdapi
A Java + Spring serverless REST API that shows Waltti region itineraries and disruption info that concerns itineraries.

The itineraries and disruption info are fetched from the [HSL GraphQL Routing API](https://digitransit.fi/en/developers/apis/1-routing-api/) (&copy; HSL 2023).

## Example requests
To fetch itineraries between two points A and B:
```
/api/v1/itineraries?sLat=<A.latitude>&sLon=<A.longitude>&eLat=<B.latitude>&eLon=<B.longitude>
```

To fetch disruptions for itineraries between two points A and B:
```
/api/v1/disruptions?sLat=<A.latitude>&sLon=<A.longitude>&eLat=<B.latitude>&eLon=<B.longitude>
```

