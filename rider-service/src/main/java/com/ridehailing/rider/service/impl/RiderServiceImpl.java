package com.ridehailing.rider.service.impl;

import com.ridehailing.rider.dto.RiderRegistrationRequest;
import com.ridehailing.rider.dto.RiderResponse;
import com.ridehailing.rider.entity.Rider;
import com.ridehailing.rider.exception.RiderNotFoundException;
import com.ridehailing.rider.repository.RiderRepository;
import com.ridehailing.rider.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final RiderRepository riderRepository;

    @Override
    @Transactional
    public RiderResponse registerRider(RiderRegistrationRequest request) {
        if (riderRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (riderRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already registered");
        }

        Rider rider = new Rider();
        rider.setFirstName(request.getFirstName());
        rider.setLastName(request.getLastName());
        rider.setEmail(request.getEmail());
        rider.setPhoneNumber(request.getPhoneNumber());
        rider.setCurrentLocation(request.getCurrentLocation());

        rider = riderRepository.save(rider);
        return convertToResponse(rider);
    }

    @Override
    @Transactional(readOnly = true)
    public RiderResponse getRiderById(Long id) {
        Rider rider = riderRepository.findById(id)
                .orElseThrow(() -> new RiderNotFoundException("Rider not found with id: " + id));
        return convertToResponse(rider);
    }

    @Override
    @Transactional
    public RiderResponse updateRiderLocation(Long id, String location) {
        Rider rider = riderRepository.findById(id)
                .orElseThrow(() -> new RiderNotFoundException("Rider not found with id: " + id));
        rider.setCurrentLocation(location);
        rider = riderRepository.save(rider);
        return convertToResponse(rider);
    }

    @Override
    @Transactional
    public void deleteRider(Long id) {
        if (!riderRepository.existsById(id)) {
            throw new RiderNotFoundException("Rider not found with id: " + id);
        }
        riderRepository.deleteById(id);
    }

    private RiderResponse convertToResponse(Rider rider) {
        RiderResponse response = new RiderResponse();
        response.setId(rider.getId());
        response.setFirstName(rider.getFirstName());
        response.setLastName(rider.getLastName());
        response.setEmail(rider.getEmail());
        response.setPhoneNumber(rider.getPhoneNumber());
        response.setCurrentLocation(rider.getCurrentLocation());
        return response;
    }
}