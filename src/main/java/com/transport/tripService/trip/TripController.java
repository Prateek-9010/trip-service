package com.transport.tripService.trip;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;
    private final TripRepository tripRepository;

    public TripController(TripService tripService,
                          TripRepository tripRepository) {
        this.tripService = tripService;
        this.tripRepository = tripRepository;
    }

    /**
     * Create a new trip
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trip createTrip(@RequestBody CreateTripRequest request) {

        return tripService.createTrip(
                request.getVehicleId(),
                request.getSource(),
                request.getDestination(),
                request.getRevenue()
        );
    }

    /**
     * Start trip
     */
    @PostMapping("/{id}/start")
    public Trip startTrip(@PathVariable Long id) {
        return tripService.startTrip(id);
    }

    /**
     * Complete trip
     */
    @PostMapping("/{id}/complete")
    public Trip completeTrip(@PathVariable Long id) {
        return tripService.completeTrip(id);
    }

    /**
     * Get all trips
     */
    @GetMapping
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
}