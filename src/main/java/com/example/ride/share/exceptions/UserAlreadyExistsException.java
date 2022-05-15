package com.example.ride.share.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    @Override
    public String getMessage(){
        return "User already exists";
    }
}
