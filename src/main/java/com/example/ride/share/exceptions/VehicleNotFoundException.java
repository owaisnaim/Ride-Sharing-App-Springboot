package com.example.ride.share.exceptions;

public class VehicleNotFoundException extends RuntimeException{

    @Override
    public String getMessage(){
        return "Vehicle not found";
    }
}
