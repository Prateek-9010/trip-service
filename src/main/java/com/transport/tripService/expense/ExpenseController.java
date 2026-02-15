package com.transport.tripService.expense;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseService expenseService,
                             ExpenseRepository expenseRepository) {
        this.expenseService = expenseService;
        this.expenseRepository = expenseRepository;
    }

    /**
     * Add expense to a trip
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense addExpense(@Valid @RequestBody AddExpenseRequest request) {

        return expenseService.addExpense(
                request.getTripId(),
                request.getCategoryId(),
                request.getAmount(),
                request.getDescription(),
                request.getPaidBy()
        );
    }

    /**
     * Get all expenses for a trip
     */
    @GetMapping("/trip/{tripId}")
    public List<Expense> getExpensesByTrip(@PathVariable Long tripId) {
        return expenseRepository.findByTripId(tripId);
    }
}