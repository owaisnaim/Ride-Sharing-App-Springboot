package com.example.ride.share.exceptions;

public class VehicleAlreadyExistsException extends RuntimeException{

    @Override
    public String getMessage(){
        return "Vehicle already exists";
    }
}
