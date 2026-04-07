package com.transport.tripService.trip;

import java.math.BigDecimal;
import java.util.List;

public class TripLedgerResponse {

    private Long tripId;
    private String from;
    private String to;
    private String driverName;
    private String vehicleNumber;
    private String status;
    private BigDecimal fareAmount;
    private BigDecimal totalExpenses;
    private BigDecimal profit;
    private List<LedgerExpenseDTO> expenses;

    // === No-arg constructor ===
    public TripLedgerResponse() {}

    // === Getters and Setters ===

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(BigDecimal fareAmount) {
        this.fareAmount = fareAmount;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public List<LedgerExpenseDTO> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<LedgerExpenseDTO> expenses) {
        this.expenses = expenses;
    }
}