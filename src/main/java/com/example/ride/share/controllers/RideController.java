package com.example.ride.share.controllers;

import com.example.ride.share.entities.Ride;
import com.example.ride.share.enums.MatchRideParameter;
import com.example.ride.share.service.RideService;
import com.example.ride.share.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;

    @PostMapping("/offer")
    public ResponseEntity offerRide(@NonNull final String username,
                                    @NonNull final String origin,
                                    @NonNull final Integer noOfSeatsAvailable,
                                    @NonNull final String vehicleType,
                                    @NonNull final String vehicleNo,
                                    @NonNull final String destination){
        rideService.offerRide(origin, destination, username, vehicleNo, vehicleType, noOfSeatsAvailable);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/select")
    public ResponseEntity<?> selectRide(@NonNull final String username,
                                        @NonNull final String origin,
                                        @NonNull final String destination,
                                        @NonNull final Integer noOfSeats,
                                        final String preferredVehicle,
                                        @NonNull final MatchRideParameter matchRideParameter){
        Ride ride = rideService.matchRide(username, origin, destination, noOfSeats, preferredVehicle, matchRideParameter);
        if(ride != null){
            return new ResponseEntity<>(ride, HttpStatus.OK);
        }
        else{
            List<Ride> ridesBreak = rideService.breakJourney(username, origin, destination, noOfSeats);
            return new ResponseEntity<>(ridesBreak, HttpStatus.OK);
        }
    }

    @GetMapping("/getRides")
    public ResponseEntity<?> getAllRides(){
        return new ResponseEntity<>(rideService.getRideStats(), HttpStatus.OK);
    }

    @PostMapping("/end")
    public ResponseEntity endRide(@NonNull final String vehicleNo){
        rideService.endRide(vehicleNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
