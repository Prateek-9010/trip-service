package com.transport.tripService.vehicle;

import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Register a new vehicle.
     * Business rule: vehicle number must be unique.
     */
    public Vehicle registerVehicle(String vehicleNumber, String ownerName, Double capacity) {

        vehicleRepository.findByVehicleNumber(vehicleNumber)
                .ifPresent(v -> {
                    throw new IllegalArgumentException(
                            "Vehicle with number " + vehicleNumber + " already exists"
                    );
                });

        Vehicle vehicle = new Vehicle(vehicleNumber, ownerName, capacity);
        return vehicleRepository.save(vehicle);
    }
}