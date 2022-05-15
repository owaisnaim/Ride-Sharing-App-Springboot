package com.example.ride.share.model;

import lombok.Data;

@Data
public class ResponseRide {
    private String username;
    private Integer ridesTaken;
    private Integer ridesOffered;
}
