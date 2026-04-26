package com.transport.tripService.expense;

import com.transport.tripService.common.BadRequestException;
import com.transport.tripService.common.ResourceNotFoundException;
import com.transport.tripService.driver.Driver;
import com.transport.tripService.driver.DriverService;
import com.transport.tripService.trip.Trip;
import com.transport.tripService.trip.TripRepository;
import com.transport.tripService.trip.TripService;
import com.transport.tripService.trip.TripStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository categoryRepository;
    private final TripService tripService;
    private final DriverService driverService;
    private final TripRepository tripRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          ExpenseCategoryRepository categoryRepository,
                          TripService tripService,
                          DriverService driverService,
                          TripRepository tripRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.tripService = tripService;
        this.driverService = driverService;
        this.tripRepository = tripRepository;
    }

    // ============================================================
    // EXISTING: Add expense by IDs (Developer API)
    // ============================================================
    @Transactional
    public ExpenseResponse addExpense(AddExpenseRequest request) {
        Trip trip = tripService.getTripEntityById(request.getTripId());

        validateTripIsActive(trip);

        ExpenseCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense category not found with id: " + request.getCategoryId()));

        Expense expense = buildAndSaveExpense(trip, category, request.getAmount(), request.getDescription());
        return ExpenseResponse.fromEntity(expense);
    }

    // ============================================================
    // NEW: Add expense by Phone (WhatsApp API)
    // ============================================================
    @Transactional
    public ExpenseResponse addExpenseByPhone(AddExpenseByPhoneRequest request) {

        // Step 1: Phone → Driver
        Driver driver = driverService.getDriverByPhone(request.getPhone());

        // Step 2: Driver → Active Trip
        Trip activeTrip = tripRepository.findByDriverIdAndStatus(driver.getId(), TripStatus.STARTED)
                .orElseThrow(() -> new BadRequestException(
                        "No active trip found for driver: " + driver.getName()
                                + ". Start a trip first before adding expenses."));

        // Step 3: Category Name → Category Entity
        ExpenseCategory category = categoryRepository
                .findByNameIgnoreCase(request.getCategoryName().trim())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Unknown expense category: '" + request.getCategoryName()
                                + "'. Available categories: " + getAvailableCategoryNames()));

        // Step 4: Build and save
        Expense expense = buildAndSaveExpense(
                activeTrip, category, request.getAmount(), request.getDescription());

        return ExpenseResponse.fromEntity(expense);
    }

    // ============================================================
    // EXISTING: Get expenses by trip
    // ============================================================
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByTripId(Long tripId) {
        tripService.getTripEntityById(tripId);

        List<Expense> expenses = expenseRepository.findByTripId(tripId);

        return expenses.stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // ============================================================
    // NEW: Get all available categories
    // ============================================================
    @Transactional(readOnly = true)
    public List<ExpenseCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    // ============================================================
    // PRIVATE: Shared expense building logic
    // ============================================================
    private Expense buildAndSaveExpense(Trip trip, ExpenseCategory category,
                                         java.math.BigDecimal amount, String description) {
        Expense expense = new Expense();
        expense.setTrip(trip);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setDescription(description);

        return expenseRepository.save(expense);
    }

    // ============================================================
    // PRIVATE: Trip must be STARTED to add expenses
    // ============================================================
    private void validateTripIsActive(Trip trip) {
        if (trip.getStatus() != TripStatus.STARTED) {
            throw new BadRequestException(
                    "Cannot add expense to trip with status: " + trip.getStatus()
                            + ". Trip must be in STARTED status.");
        }
    }

    // ============================================================
    // PRIVATE: Helper to build category list for error messages
    // ============================================================
    private String getAvailableCategoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(ExpenseCategory::getName)
                .collect(Collectors.joining(", "));
    }
}