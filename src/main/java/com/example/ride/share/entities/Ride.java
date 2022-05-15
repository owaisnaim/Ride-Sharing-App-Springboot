package com.example.ride.share.entities;

import com.example.ride.share.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.ride.share.enums.RideStatus.UNAVAILABLE;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ride {

    private String origin;
    private String destination;
    private User user;
    private Vehicle vehicle;
    private int noOfSeatsAvailable;
    private RideStatus rideStatus;

    public void endRide(){
        this.rideStatus = UNAVAILABLE;
    }
}
