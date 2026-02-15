package com.transport.tripService.trip;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.transport.tripService.expense.ExpenseService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;
    private final TripRepository tripRepository;
    private final ExpenseService expenseService;

    public TripController(TripService tripService,
            TripRepository tripRepository,
            ExpenseService expenseService) {
        this.tripService = tripService;
        this.tripRepository = tripRepository;
        this.expenseService = expenseService;
    }

    /**
     * Create a new trip
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TripResponse createTrip(@Valid @RequestBody CreateTripRequest request) {

        return tripService.createTrip(
                request.getVehicleId(),
                request.getSource(),
                request.getDestination(),
                request.getRevenue());
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

    @GetMapping("/{id}/summary")
    public TripFinancialSummary getTripSummary(@PathVariable Long id) {

        Double totalExpense = expenseService.getTotalExpenseForTrip(id);

        return tripService.getFinancialSummary(id, totalExpense);
    }

    @GetMapping("/{id}/ledger")
    public TripLedgerResponse getTripLedger(@PathVariable Long id) {
        return tripService.getLedger(id);
    }
}