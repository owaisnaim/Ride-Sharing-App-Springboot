package com.example.ride.share.service;

import com.example.ride.share.entities.User;
import com.example.ride.share.entities.Vehicle;
import com.example.ride.share.exceptions.UserNotFoundException;
import com.example.ride.share.exceptions.VehicleAlreadyExistsException;
import com.example.ride.share.exceptions.VehicleNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final UserService userService;
    Map<String, Vehicle> vehicles = new HashMap<>();

    public synchronized void addVehicle(String username, String vehicleType, String vehicleNo){
        User user = null;
        try{
            user = userService.getUser(username);
        }
        catch (UserNotFoundException e){
            System.out.println("User not found, please add user first");
        }
        Vehicle newVehicle = new Vehicle(user, vehicleNo, vehicleType);
        if(vehicles.containsKey(newVehicle.getVehicleNo())) {
            throw new VehicleAlreadyExistsException();
        }
        vehicles.put(newVehicle.getVehicleNo(), newVehicle);
    }

    public Vehicle getVehicle(@NonNull final String vehicleNo){
        if(!vehicles.containsKey(vehicleNo)){
            throw new VehicleNotFoundException();
        }
        return vehicles.get(vehicleNo);
    }
}
