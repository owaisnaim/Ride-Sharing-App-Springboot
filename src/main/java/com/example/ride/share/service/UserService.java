package com.example.ride.share.service;

import com.example.ride.share.entities.Ride;
import com.example.ride.share.entities.User;
import com.example.ride.share.exceptions.UserAlreadyExistsException;
import com.example.ride.share.exceptions.UserNotFoundException;
import com.example.ride.share.model.ResponseRide;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    Map<String, User> users = new HashMap<>();

    public synchronized void addUser(@NonNull final User newUser){
        if(users.containsKey(newUser.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        users.put(newUser.getUsername(), newUser);
    }

    public User getUser(@NonNull final String username){
        if(!users.containsKey(username)){
            throw new UserNotFoundException();
        }
        return users.get(username);
    }

}
