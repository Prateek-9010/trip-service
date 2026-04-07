package com.transport.tripService.expense;

import com.transport.tripService.common.ResourceNotFoundException;
import com.transport.tripService.trip.Trip;
import com.transport.tripService.trip.TripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository categoryRepository;
    private final TripService tripService;

    public ExpenseService(ExpenseRepository expenseRepository,
                          ExpenseCategoryRepository categoryRepository,
                          TripService tripService) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.tripService = tripService;
    }

    @Transactional
    public ExpenseResponse addExpense(AddExpenseRequest request) {
        Trip trip = tripService.getTripEntityById(request.getTripId());

        ExpenseCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense category not found with id: " + request.getCategoryId()));

        Expense expense = new Expense();
        expense.setTrip(trip);
        expense.setCategory(category);
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());

        Expense saved = expenseRepository.save(expense);

        // Convert to DTO INSIDE the transaction (Hibernate session is still open)
        return ExpenseResponse.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByTripId(Long tripId) {
        // Verify trip exists first
        tripService.getTripEntityById(tripId);

        List<Expense> expenses = expenseRepository.findByTripId(tripId);

        // Convert to DTOs INSIDE the transaction
        return expenses.stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }
}