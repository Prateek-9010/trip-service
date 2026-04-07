package com.transport.tripService.trip;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LedgerExpenseDTO {

    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDateTime recordedAt;

    // === No-arg constructor (required) ===
    public LedgerExpenseDTO() {}

    // === Convenience constructor ===
    public LedgerExpenseDTO(String category, BigDecimal amount,
                            String description, LocalDateTime recordedAt) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.recordedAt = recordedAt;
    }

    // === Getters and Setters ===

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}