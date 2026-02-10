package com.transport.tripService.vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Register a new vehicle
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle registerVehicle(@RequestBody CreateVehicleRequest request) {

        return vehicleService.registerVehicle(
                request.getVehicleNumber(),
                request.getOwnerName(),
                request.getCapacity()
        );
    }
}