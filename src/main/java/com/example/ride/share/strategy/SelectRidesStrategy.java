package com.example.ride.share.strategy;

import com.example.ride.share.entities.Ride;
import com.example.ride.share.enums.MatchRideParameter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ride.share.enums.MatchRideParameter.*;

@RequiredArgsConstructor
@Service
public class SelectRidesStrategy {

    public Ride selectRide(@NonNull final List<Ride> potentialRides,
                           final String preferredVehicleType,
                           final MatchRideParameter matchRideParameter){
        if (matchRideParameter == VEHICLE_TYPE){
            return selectRideByVehicleType(potentialRides, preferredVehicleType);
        } else {
            return selectRideByVacantSeats(potentialRides);
        }
    }

    public Ride selectRideByVacantSeats(@NonNull final List<Ride> potentialRides) {
        if(potentialRides.isEmpty()){
            return null;
        }
        Ride selectedRide = null;
        int maxVacantSeats = 0;
        for (Ride ride : potentialRides){
            if(ride.getNoOfSeatsAvailable() >= maxVacantSeats){
                maxVacantSeats = ride.getNoOfSeatsAvailable();
                selectedRide = ride;
            }
        }
        return selectedRide;
    }

    public Ride selectRideByVehicleType(@NonNull final List<Ride> potentialRides,
                           @NonNull final String preferredVehicleType) {
        if(potentialRides.isEmpty()){
            return null;
        }
        for (Ride ride : potentialRides){
            if(ride.getVehicle().getVehicleType() == preferredVehicleType){
                return ride;
            }
        }
        return null;
    }
}
