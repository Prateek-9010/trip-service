package com.transport.tripService.trip;

import java.util.List;

public class TripLedgerResponse {

    private Long tripId;
    private String vehicleNumber;
    private String source;
    private String destination;
    private Double revenue;
    private List<LedgerExpenseDTO> expenses;
    private Double totalExpense;
    private Double profit;

    public TripLedgerResponse(Long tripId,
                              String vehicleNumber,
                              String source,
                              String destination,
                              Double revenue,
                              List<LedgerExpenseDTO> expenses,
                              Double totalExpense,
                              Double profit) {
        this.tripId = tripId;
        this.vehicleNumber = vehicleNumber;
        this.source = source;
        this.destination = destination;
        this.revenue = revenue;
        this.expenses = expenses;
        this.totalExpense = totalExpense;
        this.profit = profit;
    }

    public Long getTripId() { return tripId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public Double getRevenue() { return revenue; }
    public List<LedgerExpenseDTO> getExpenses() { return expenses; }
    public Double getTotalExpense() { return totalExpense; }
    public Double getProfit() { return profit; }
}