package com.ridehailing.rider.service;

import com.ridehailing.rider.dto.RiderRegistrationRequest;
import com.ridehailing.rider.dto.RiderResponse;

public interface RiderService {
    RiderResponse registerRider(RiderRegistrationRequest request);
    RiderResponse getRiderById(Long id);
    RiderResponse updateRiderLocation(Long id, String location);
    void deleteRider(Long id);
}