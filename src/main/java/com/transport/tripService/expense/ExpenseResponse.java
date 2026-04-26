package com.transport.tripService.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseResponse {

    private Long id;
    private Long tripId;
    private String tripRoute;
    private String driverName;
    private String categoryName;
    private Long categoryId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;

    public ExpenseResponse() {}

    public static ExpenseResponse fromEntity(Expense expense) {
        ExpenseResponse response = new ExpenseResponse();
        response.setId(expense.getId());
        response.setAmount(expense.getAmount());
        response.setDescription(expense.getDescription());
        response.setCreatedAt(expense.getCreatedAt());

        if (expense.getTrip() != null) {
            response.setTripId(expense.getTrip().getId());
            response.setTripRoute(
                    expense.getTrip().getFromLocation() + " → " + expense.getTrip().getToLocation());
            if (expense.getTrip().getDriver() != null) {
                response.setDriverName(expense.getTrip().getDriver().getName());
            }
        }

        if (expense.getCategory() != null) {
            response.setCategoryId(expense.getCategory().getId());
            response.setCategoryName(expense.getCategory().getName());
        }

        return response;
    }

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getTripRoute() {
        return tripRoute;
    }

    public void setTripRoute(String tripRoute) {
        this.tripRoute = tripRoute;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}