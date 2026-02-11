package com.transport.tripService.trip;

import com.transport.tripService.vehicle.Vehicle;
import com.transport.tripService.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;

    public TripService(TripRepository tripRepository,
                       VehicleRepository vehicleRepository) {
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Create a new trip for a vehicle.
     */
    public Trip createTrip(Long vehicleId,
                           String source,
                           String destination,
                           Double revenue) {

        // 1️⃣ Validate vehicle exists
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Vehicle not found with id: " + vehicleId)
                );

        // 2️⃣ Create trip
        Trip trip = new Trip(vehicle, source, destination, revenue);

        // 3️⃣ Save and return
        return tripRepository.save(trip);
    }

    /**
     * Start a trip.
     */
    public Trip startTrip(Long tripId) {

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Trip not found with id: " + tripId)
                );

        trip.startTrip();

        return tripRepository.save(trip);
    }

    /**
     * Complete a trip.
     */
    public Trip completeTrip(Long tripId) {

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Trip not found with id: " + tripId)
                );

        trip.completeTrip();

        return tripRepository.save(trip);
    }
}