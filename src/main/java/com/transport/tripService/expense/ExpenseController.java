package com.transport.tripService.expense;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // === Developer API: Add by IDs ===
    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(
            @Valid @RequestBody AddExpenseRequest request) {
        ExpenseResponse response = expenseService.addExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // === WhatsApp API: Add by Phone + Category Name ===
    @PostMapping("/by-phone")
    public ResponseEntity<ExpenseResponse> addExpenseByPhone(
            @Valid @RequestBody AddExpenseByPhoneRequest request) {
        ExpenseResponse response = expenseService.addExpenseByPhone(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // === Get expenses for a trip ===
    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByTrip(
            @PathVariable Long tripId) {
        return ResponseEntity.ok(expenseService.getExpensesByTripId(tripId));
    }

    // === Get all available categories ===
    @GetMapping("/categories")
    public ResponseEntity<List<ExpenseCategory>> getCategories() {
        return ResponseEntity.ok(expenseService.getAllCategories());
    }
}