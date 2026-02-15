package com.transport.tripService.trip;

import com.transport.tripService.expense.ExpenseRepository;
import com.transport.tripService.vehicle.Vehicle;
import com.transport.tripService.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;
import com.transport.tripService.common.ResourceNotFoundException;

@Service
public class TripService {

        private final TripRepository tripRepository;
        private final VehicleRepository vehicleRepository;
        private final ExpenseRepository expenseRepository;

        public TripService(TripRepository tripRepository,
                        VehicleRepository vehicleRepository,
                        ExpenseRepository expenseRepository) {
                this.tripRepository = tripRepository;
                this.vehicleRepository = vehicleRepository;
                this.expenseRepository = expenseRepository;
        }

        private TripResponse mapToResponse(Trip trip) {
                return new TripResponse(
                                trip.getId(),
                                trip.getVehicle().getVehicleNumber(),
                                trip.getSource(),
                                trip.getDestination(),
                                trip.getRevenue(),
                                trip.getStatus().name());
        }

        /**
         * Create a new trip for a vehicle.
         */
        public TripResponse createTrip(Long vehicleId,
                        String source,
                        String destination,
                        Double revenue) {

                // 1️⃣ Validate vehicle exists
                Vehicle vehicle = vehicleRepository.findById(vehicleId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Vehicle not found with id: " + vehicleId));

                // 2️⃣ Create trip
                Trip trip = new Trip(vehicle, source, destination, revenue);

                // 3️⃣ Save and return
                Trip savedTrip = tripRepository.save(trip);
                return mapToResponse(savedTrip);
        }

        /**
         * Start a trip.
         */
        public Trip startTrip(Long tripId) {

                Trip trip = tripRepository.findById(tripId)
                                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

                trip.startTrip();

                return tripRepository.save(trip);
        }

        /**
         * Complete a trip.
         */
        public Trip completeTrip(Long tripId) {

                Trip trip = tripRepository.findById(tripId)
                                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

                trip.completeTrip();

                return tripRepository.save(trip);
        }

        public TripFinancialSummary getFinancialSummary(Long tripId,
                        Double totalExpense) {

                Trip trip = tripRepository.findById(tripId)
                                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

                Double profit = trip.getRevenue() - totalExpense;

                return new TripFinancialSummary(
                                trip.getId(),
                                trip.getVehicle().getVehicleNumber(),
                                trip.getSource(),
                                trip.getDestination(),
                                trip.getRevenue(),
                                totalExpense,
                                profit);
        }

        public TripLedgerResponse getLedger(Long tripId) {

                Trip trip = tripRepository.findById(tripId)
                                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id: " + tripId));

                var expenses = expenseRepository.findByTripId(tripId);

                var expenseDTOs = expenses.stream()
                                .map(e -> new LedgerExpenseDTO(
                                                e.getCategory().getName(),
                                                e.getAmount(),
                                                e.getDescription()))
                                .toList();

                Double totalExpense = expenseRepository.getTotalExpenseForTrip(tripId);
                Double profit = trip.getRevenue() - totalExpense;

                return new TripLedgerResponse(
                                trip.getId(),
                                trip.getVehicle().getVehicleNumber(),
                                trip.getSource(),
                                trip.getDestination(),
                                trip.getRevenue(),
                                expenseDTOs,
                                totalExpense,
                                profit);
        }

}