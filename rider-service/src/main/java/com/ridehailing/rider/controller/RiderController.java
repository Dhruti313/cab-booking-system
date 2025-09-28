package com.ridehailing.rider.controller;

import com.ridehailing.rider.dto.RiderRegistrationRequest;
import com.ridehailing.rider.dto.RiderResponse;
import com.ridehailing.rider.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping
    public ResponseEntity<RiderResponse> registerRider(@Valid @RequestBody RiderRegistrationRequest request) {
        return new ResponseEntity<>(riderService.registerRider(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiderResponse> getRider(@PathVariable Long id) {
        return ResponseEntity.ok(riderService.getRiderById(id));
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<RiderResponse> updateLocation(
            @PathVariable Long id,
            @RequestParam String location) {
        return ResponseEntity.ok(riderService.updateRiderLocation(id, location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRider(@PathVariable Long id) {
        riderService.deleteRider(id);
        return ResponseEntity.noContent().build();
    }
}