package com.ridehailing.rider.dto;

import lombok.Data;

@Data
public class RiderResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String currentLocation;
}