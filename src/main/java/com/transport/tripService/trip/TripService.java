package com.transport.tripService.trip;

import com.transport.tripService.common.BadRequestException;
import com.transport.tripService.common.ResourceNotFoundException;
import com.transport.tripService.driver.Driver;
import com.transport.tripService.driver.DriverService;
import com.transport.tripService.expense.Expense;
import com.transport.tripService.expense.ExpenseRepository;
import com.transport.tripService.vehicle.Vehicle;
import com.transport.tripService.vehicle.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final ExpenseRepository expenseRepository;

    public TripService(TripRepository tripRepository,
                       VehicleService vehicleService,
                       DriverService driverService,
                       ExpenseRepository expenseRepository) {
        this.tripRepository = tripRepository;
        this.vehicleService = vehicleService;
        this.driverService = driverService;
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public TripResponse createTrip(CreateTripRequest request) {
        Vehicle vehicle = vehicleService.getVehicleById(request.getVehicleId());
        Driver driver = driverService.getDriverById(request.getDriverId());

        validateDriverHasNoActiveTrip(driver.getId());

        Trip trip = new Trip();
        trip.setFromLocation(request.getFromLocation());
        trip.setToLocation(request.getToLocation());
        trip.setFareAmount(request.getFareAmount());
        trip.setVehicle(vehicle);
        trip.setDriver(driver);

        Trip saved = tripRepository.save(trip);
        return TripResponse.fromEntity(saved);
    }

    @Transactional
    public TripResponse startTrip(Long tripId) {
        Trip trip = getTripEntityById(tripId);

        if (trip.getStatus() != TripStatus.CREATED) {
            throw new BadRequestException(
                    "Trip can only be started from CREATED status. Current: "
                            + trip.getStatus());
        }

        boolean hasOtherActiveTrip = tripRepository.existsByDriverIdAndStatus(
                trip.getDriver().getId(), TripStatus.STARTED);

        if (hasOtherActiveTrip) {
            throw new BadRequestException(
                    "Driver " + trip.getDriver().getName()
                            + " already has an active trip in STARTED status");
        }

        trip.setStatus(TripStatus.STARTED);
        trip.setStartedAt(LocalDateTime.now());

        Trip saved = tripRepository.save(trip);
        return TripResponse.fromEntity(saved);
    }

    @Transactional
    public TripResponse completeTrip(Long tripId) {
        Trip trip = getTripEntityById(tripId);

        if (trip.getStatus() != TripStatus.STARTED) {
            throw new BadRequestException(
                    "Trip can only be completed from STARTED status. Current: "
                            + trip.getStatus());
        }

        trip.setStatus(TripStatus.COMPLETED);
        trip.setCompletedAt(LocalDateTime.now());

        Trip saved = tripRepository.save(trip);
        return TripResponse.fromEntity(saved);
    }

    public List<TripResponse> getAllTrips() {
        return tripRepository.findAll()
                .stream()
                .map(TripResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public TripResponse getTripById(Long id) {
        Trip trip = getTripEntityById(id);
        return TripResponse.fromEntity(trip);
    }

    public TripResponse getActiveTripForDriver(Long driverId) {
        driverService.getDriverById(driverId);

        Trip trip = tripRepository.findByDriverIdAndStatus(driverId, TripStatus.STARTED)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active trip found for driver with id: " + driverId));

        return TripResponse.fromEntity(trip);
    }

    public TripResponse getActiveTripForDriverPhone(String phone) {
        Driver driver = driverService.getDriverByPhone(phone);

        Trip trip = tripRepository.findByDriverIdAndStatus(
                        driver.getId(), TripStatus.STARTED)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active trip found for driver with phone: " + phone));

        return TripResponse.fromEntity(trip);
    }

    // ============================================================
    // FINANCIAL SUMMARY
    // ============================================================
    public TripFinancialSummary getFinancialSummary(Long tripId) {
        Trip trip = getTripEntityById(tripId);
        List<Expense> expenses = expenseRepository.findByTripId(tripId);

        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal profit = trip.getFareAmount().subtract(totalExpenses);

        TripFinancialSummary summary = new TripFinancialSummary();
        summary.setTripId(trip.getId());
        summary.setFrom(trip.getFromLocation());
        summary.setTo(trip.getToLocation());
        summary.setStatus(trip.getStatus().name());
        summary.setFareAmount(trip.getFareAmount());
        summary.setTotalExpenses(totalExpenses);
        summary.setProfit(profit);
        summary.setExpenseCount(expenses.size());

        return summary;
    }

    // ============================================================
    // LEDGER
    // ============================================================
    public TripLedgerResponse getLedger(Long tripId) {
        Trip trip = getTripEntityById(tripId);
        List<Expense> expenses = expenseRepository.findByTripId(tripId);

        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<LedgerExpenseDTO> ledgerExpenses = expenses.stream()
                .map(e -> new LedgerExpenseDTO(
                        e.getCategory().getName(),
                        e.getAmount(),
                        e.getDescription(),
                        e.getCreatedAt()
                ))
                .collect(Collectors.toList());

        TripLedgerResponse ledger = new TripLedgerResponse();
        ledger.setTripId(trip.getId());
        ledger.setFrom(trip.getFromLocation());
        ledger.setTo(trip.getToLocation());
        ledger.setDriverName(trip.getDriver().getName());
        ledger.setVehicleNumber(trip.getVehicle().getRegistrationNumber());
        ledger.setStatus(trip.getStatus().name());
        ledger.setFareAmount(trip.getFareAmount());
        ledger.setTotalExpenses(totalExpenses);
        ledger.setProfit(trip.getFareAmount().subtract(totalExpenses));
        ledger.setExpenses(ledgerExpenses);

        return ledger;
    }

    // ============================================================
    // INTERNAL — package-private, used by ExpenseService
    // ============================================================
    public Trip getTripEntityById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trip not found with id: " + id));
    }

    // ============================================================
    // PRIVATE — Business invariant
    // ============================================================
        private void validateDriverHasNoActiveTrip(Long driverId) {
        boolean hasCreatedTrip = tripRepository.existsByDriverIdAndStatus(
                driverId, TripStatus.CREATED);
        boolean hasStartedTrip = tripRepository.existsByDriverIdAndStatus(
                driverId, TripStatus.STARTED);

        if (hasCreatedTrip) {
            throw new BadRequestException(
                    "Driver already has a trip in CREATED status. "
                            + "Start, complete, or cancel it first.");
        }

        if (hasStartedTrip) {
            throw new BadRequestException(
                    "Driver already has an active trip in STARTED status. "
                            + "Complete it first.");
        }
    }

    // ============================================================
    // TRIP CANCEL — Only from CREATED status
    // ============================================================
    @Transactional
    public TripResponse cancelTrip(Long tripId) {
        Trip trip = getTripEntityById(tripId);

        if (trip.getStatus() != TripStatus.CREATED) {
            throw new BadRequestException(
                    "Trip can only be cancelled from CREATED status. Current: "
                            + trip.getStatus()
                            + ". If trip is STARTED, it must be completed.");
        }

        trip.setStatus(TripStatus.CANCELLED);

        Trip saved = tripRepository.save(trip);
        return TripResponse.fromEntity(saved);
    }
    
}