package com.example.ride.share.controllers;

import com.example.ride.share.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/add")
    public ResponseEntity addVehicle(String username, String vehicleType, String vehicleNo){
        vehicleService.addVehicle(username, vehicleType, vehicleNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
