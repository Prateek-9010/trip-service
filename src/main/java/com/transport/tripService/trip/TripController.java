package com.transport.tripService.trip;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(
            @Valid @RequestBody CreateTripRequest request) {
        TripResponse response = tripService.createTrip(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<TripResponse> startTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.startTrip(id));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<TripResponse> completeTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.completeTrip(id));
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getTripById(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<TripFinancialSummary> getFinancialSummary(
            @PathVariable Long id) {
        return ResponseEntity.ok(tripService.getFinancialSummary(id));
    }

    @GetMapping("/{id}/ledger")
    public ResponseEntity<TripLedgerResponse> getLedger(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getLedger(id));
    }

    @GetMapping("/driver/{driverId}/active")
    public ResponseEntity<TripResponse> getActiveTripForDriver(
            @PathVariable Long driverId) {
        return ResponseEntity.ok(tripService.getActiveTripForDriver(driverId));
    }

    @GetMapping("/driver/phone/{phone}/active")
    public ResponseEntity<TripResponse> getActiveTripByPhone(
            @PathVariable String phone) {
        return ResponseEntity.ok(tripService.getActiveTripForDriverPhone(phone));
    }
}