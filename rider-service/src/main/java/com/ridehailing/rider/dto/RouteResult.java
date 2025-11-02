package com.ridehailing.rider.dto;

import com.graphhopper.util.PointList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteResult {
    private double distance;    // Distance in meters
    private long timeInMs;     // Time in milliseconds
    private PointList points;  // List of points along the route
}