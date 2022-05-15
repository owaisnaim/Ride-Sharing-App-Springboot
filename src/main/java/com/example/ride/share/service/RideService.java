package com.example.ride.share.service;

import com.example.ride.share.entities.Ride;
import com.example.ride.share.entities.User;
import com.example.ride.share.entities.Vehicle;
import com.example.ride.share.enums.MatchRideParameter;
import com.example.ride.share.exceptions.NoRideFoundException;
import com.example.ride.share.exceptions.RideAlreadyExistsException;
import com.example.ride.share.model.ResponseRide;
import com.example.ride.share.strategy.SelectRidesStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ride.share.enums.RideStatus.*;

@Service
@RequiredArgsConstructor
public class RideService {

    private final SelectRidesStrategy selectRidesStrategy;

    private final UserService userService;

    private final VehicleService vehicleService;

    Map<String, Ride> rides = new HashMap<>();

    public void offerRide(String origin,
                          String destination,
                          String username,
                          String vehicleNo,
                          String vehicleType,
                          Integer noOfSeatsAvailable) {
        if(rides.containsKey(vehicleNo)){
            throw new RideAlreadyExistsException();
        }
        User user = userService.getUser(username);
        Vehicle vehicle = vehicleService.getVehicle(vehicleNo);
        vehicle.setVehicleType(vehicleType);
        Ride ride = new Ride(origin, destination, user, vehicle, noOfSeatsAvailable, AVAILABLE);
        rides.put(vehicle.getVehicleNo(), ride);
        user.setNoOfRidesOffered(user.getNoOfRidesOffered()+1);
    }

    public Ride matchRide(String username, String origin, String destination, Integer noOfSeats, String preferredVehicleType,
                          MatchRideParameter matchRideParameter){
        final List<Ride> probableRides = getRides(origin, destination, noOfSeats);
        final Ride selectedRide = selectRidesStrategy.selectRide(probableRides, preferredVehicleType, matchRideParameter);
        if(selectedRide == null || selectedRide.getRideStatus() != AVAILABLE){
            System.out.println("No rides found for "+username+". Checking break journey options");
            return null;
        }
        User user = userService.getUser(username);
        user.setNoOfRidesTaken(user.getNoOfRidesTaken()+1);
        selectedRide.setRideStatus(IN_PROGRESS);
        System.out.println("Matched Ride: "+username+" : "+selectedRide);
        return selectedRide;
    }

    public List<Ride> getRides(@NonNull final String origin,
                               @NonNull final String destination,
                               @NonNull final Integer noOfSeats){
        List<Ride> res = new ArrayList<>();
        for(Ride ride: rides.values()){
            if(ride.getRideStatus() == AVAILABLE && ride.getOrigin() == origin && ride.getDestination() == destination
                    && noOfSeats <= 2 && ride.getNoOfSeatsAvailable() >= noOfSeats){
                res.add(ride);
            }
        }
        return res;
    }

    public Ride getRide(String vehicleNo){
        if(!rides.containsKey(vehicleNo)){
            throw new NoRideFoundException();
        }
        return rides.get(vehicleNo);
    }

    public void endRide(String vehicleNo){
        Ride ride = getRide(vehicleNo);
        if(ride.getRideStatus() == UNAVAILABLE){
            System.out.println("Ride already ended");
        }
        else{
            ride.endRide();
        }
    }

    public List<Ride> breakJourney(String username, String origin, String destination, Integer noOfSeats){
        List<Ride> ridesBreak = new ArrayList<>();
        for(Ride ride: rides.values()){
            if(ride.getRideStatus() == AVAILABLE && ride.getOrigin() == origin
                    && noOfSeats <= 2 && ride.getNoOfSeatsAvailable() >= noOfSeats){
                ridesBreak.add(ride);
                break;
            }
        }
        if(ridesBreak.get(0) == null){
            System.out.println("No rides found in multiple journeys for : "+username);
            return null;
        }

        ridesBreak.add(1, null);
        for(Ride ride: rides.values()){
            if(ride.getRideStatus() == AVAILABLE && ride.getOrigin() == ridesBreak.get(0).getDestination()
                   && ride.getDestination() == destination && noOfSeats <= 2 && ride.getNoOfSeatsAvailable() >= noOfSeats){
                ridesBreak.add(1, ride);
                break;
            }
        }
        if (ridesBreak.get(1) == null){
            System.out.println("No rides found in multiple journeys for : "+username);
            return null;
        }
        User user = userService.getUser(username);
        user.setNoOfRidesTaken(user.getNoOfRidesTaken()+1);
        System.out.println("Matched Rides with breaking journey: "+username+" : "+ridesBreak);
        return ridesBreak;
    }

    public List<ResponseRide> getRideStats(){
        List<ResponseRide> rideList = new ArrayList<>();
        for(User user : userService.users.values()) {
            ResponseRide ride = new ResponseRide();
            ride.setUsername(user.getUsername());
            ride.setRidesOffered(user.getNoOfRidesOffered());
            ride.setRidesTaken(user.getNoOfRidesTaken());
            rideList.add(ride);
        }
        System.out.println("Rides List: "+rideList);
        return rideList;
    }
}
