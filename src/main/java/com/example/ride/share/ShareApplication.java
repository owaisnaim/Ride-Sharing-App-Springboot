package com.example.ride.share;

import com.example.ride.share.controllers.RideController;
import com.example.ride.share.controllers.UserController;
import com.example.ride.share.controllers.VehicleController;
import com.example.ride.share.service.RideService;
import com.example.ride.share.service.UserService;
import com.example.ride.share.service.VehicleService;
import com.example.ride.share.strategy.SelectRidesStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.ride.share.enums.MatchRideParameter.VACANT_SEATS;
import static com.example.ride.share.enums.MatchRideParameter.VEHICLE_TYPE;

@SpringBootApplication
public class ShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareApplication.class, args);
		UserService userService = new UserService();
		VehicleService vehicleService = new VehicleService(userService);
		SelectRidesStrategy selectRidesStrategy = new SelectRidesStrategy();
		RideService rideService = new RideService(selectRidesStrategy, userService, vehicleService);

		UserController userController = new UserController(userService);

		VehicleController vehicleController = new VehicleController(vehicleService);

		RideController rideController = new RideController(rideService);

		userController.addUser("Rohan", 36, "M");

		userController.addUser("Virat", 33, "M");
		vehicleController.addVehicle("Virat", "Fortuner", "DL-01-12345");

		userController.addUser("Rohit", 35, "M");
		vehicleController.addVehicle("Rohit", "Fortuner", "MH-01-12345");

		userController.addUser("Shami", 33, "M");

		vehicleController.addVehicle("Rohan", "Swift", "KA-01-12345");
		userController.addUser("Shashank", 29, "M");
		vehicleController.addVehicle("Shashank", "Baleno", "TS-05-62395");
		userController.addUser("Nandini", 29, "F");
		userController.addUser("Shipra", 27, "F");
		vehicleController.addVehicle("Shipra", "Polo", "KA-05-41491");
		vehicleController.addVehicle("Shipra","Activa", "KA-12-12332");
		userController.addUser("Rahul", 35, "M");
		vehicleController.addVehicle("Rahul", "XUV", "KA-05-1234");

		rideController.offerRide("Virat", "Delhi", 2, "Fortuner",
				"DL-01-12345", "Ajmer");

		rideController.offerRide("Rohit", "Ajmer", 2, "Fortuner",
				"MH-01-12345", "Mumbai");

		rideController.offerRide("Rohan", "Hyderabad", 2,
				"Swift", "KA-01-12345", "Bangalore");
		rideController.offerRide("Shipra", "Bangalore", 1,
				"Activa", "KA-12-12332", "Mysore");
		rideController.offerRide("Shipra", "Bangalore", 2,
				"Polo", "KA-05-41491", "Mysore");
		rideController.offerRide("Shashank", "Hyderabad", 1, "Baleno",
				"TS-05-62395", "Bangalore");

		//This call fails because the ride already exists with the specified vehicle
//		rideController.offerRide("Rohan", "Bangalore", 1,
//				"Swift", "KA-01-12345", "Pune");

		rideController.selectRide("Nandini", "Bangalore", "Mysore", 1,
				null, VACANT_SEATS);

//		This will fail as the requested people for the ride is more than 2
//		rideService.matchRide("Nandini", "Bangalore", "Mysore", 1,
//				null, VACANT_SEATS);

		// No rides found on this route
//		rideController.selectRide("Shashank", "Mumbai",
//				"Bangalore", 1, null, VACANT_SEATS);

		rideController.selectRide("Rohan", "Hyderabad", "Bangalore", 1,
				"Baleno", VEHICLE_TYPE);
//		 No rides found for the specified parameters
		rideController.selectRide("Shashank", "Hyderabad",
				"Bangalore", 1, "Polo", VEHICLE_TYPE);
		rideController.endRide("KA-05-41491");


		//To check if the user’s origin/destinations are not available directly but it’s possible via multiple rides,
		rideController.selectRide("Shami", "Delhi",
				"Mumbai", 1, "Fortuner", VEHICLE_TYPE);

		rideController.getAllRides();
	}

}
