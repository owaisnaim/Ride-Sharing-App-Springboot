package com.example.ride.share.entities;

import lombok.Data;

@Data
public class User {
    private String username;
    private int age;
    private String gender;
    private int noOfRidesOffered;
    private int noOfRidesTaken;

    public User(String username, Integer age, String gender) {
        this.username = username;
        this.age = age;
        this.gender = gender;
    }
}
