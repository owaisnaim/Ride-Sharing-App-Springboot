package com.example.ride.share.exceptions;

public class RideAlreadyExistsException extends RuntimeException{

    @Override
    public String getMessage(){
        return "Ride already exists with the specified vehicle number";
    }
}
