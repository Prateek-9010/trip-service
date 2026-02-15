package com.transport.tripService.expense;

import com.transport.tripService.trip.Trip;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ExpenseCategory category;

    @Column(nullable = false)
    private Double amount;

    private String description;

    @Column(nullable = false)
    private LocalDateTime expenseDate;

    @Column(nullable = false)
    private String paidBy; // DRIVER or OWNER

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Expense() {
    }

    public Expense(Trip trip,
            ExpenseCategory category,
            Double amount,
            String description,
            String paidBy) {

        this.trip = trip;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.paidBy = paidBy;
        this.expenseDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Trip getTrip() {
        return trip;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}