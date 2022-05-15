package com.example.ride.share.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vehicle {

    private User user;

    private String vehicleNo;

    private String vehicleType;

}
