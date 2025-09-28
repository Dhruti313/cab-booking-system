package com.ridehailing.rider.service;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Locale;

@Service
@Slf4j
public class RoutingService {

    private GraphHopper graphHopper;

    @Value("${graphhopper.osm.file:maps/your-region.osm.pbf}")
    private String osmFile;

    @PostConstruct
    public void init() {
        graphHopper = new GraphHopper();
        graphHopper.setOSMFile(osmFile);
        graphHopper.setGraphHopperLocation("graphhopper-cache");
        
        // Create profiles for different vehicle types
        graphHopper.setProfiles(
            new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false),
            new Profile("bike").setVehicle("bike").setWeighting("fastest").setTurnCosts(false)
        );
        
        // Enable speed mode
        graphHopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));
        
        // Load the graph
        graphHopper.importOrLoad();
    }

    public RouteResult calculateRoute(double fromLat, double fromLon, double toLat, double toLon) {
        GHRequest request = new GHRequest(
                fromLat, fromLon,
                toLat, toLon
        ).setProfile("car")
         .setLocale(Locale.US)
         .setAlgorithm(Parameters.Algorithms.ASTAR_BI);

        GHResponse response = graphHopper.route(request);

        if (response.hasErrors()) {
            throw new RuntimeException(response.getErrors().toString());
        }

        return RouteResult.builder()
                .distance(response.getBest().getDistance())
                .timeInMs(response.getBest().getTime())
                .points(response.getBest().getPoints())
                .build();
    }
}