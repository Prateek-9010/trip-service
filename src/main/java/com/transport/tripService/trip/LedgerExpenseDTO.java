package com.transport.tripService.trip;

public class LedgerExpenseDTO {

    private String category;
    private Double amount;
    private String description;

    public LedgerExpenseDTO(String category, Double amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String getCategory() { return category; }
    public Double getAmount() { return amount; }
    public String getDescription() { return description; }
}