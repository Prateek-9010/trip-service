package com.transport.tripService.expense;

import com.transport.tripService.trip.Trip;
import com.transport.tripService.trip.TripRepository;
import org.springframework.stereotype.Service;
import com.transport.tripService.common.ResourceNotFoundException;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository categoryRepository;
    private final TripRepository tripRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          ExpenseCategoryRepository categoryRepository,
                          TripRepository tripRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.tripRepository = tripRepository;
    }

    public Expense addExpense(Long tripId,
                              Long categoryId,
                              Double amount,
                              String description,
                              String paidBy) {

        // Validate trip exists
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip not found with id: " + tripId)
                );

        // Validate category exists
        ExpenseCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense category not found with id: " + categoryId)
                );

        Expense expense = new Expense(
                trip,
                category,
                amount,
                description,
                paidBy
        );

        return expenseRepository.save(expense);
    }

    public Double getTotalExpenseForTrip(Long tripId) {
        return expenseRepository.getTotalExpenseForTrip(tripId);
    }
}