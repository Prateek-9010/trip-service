package com.transport.tripService.trip;

public class TripFinancialSummary {

    private Long tripId;
    private String vehicleNumber;
    private String source;
    private String destination;
    private Double revenue;
    private Double totalExpense;
    private Double profit;

    public TripFinancialSummary(Long tripId,
                                String vehicleNumber,
                                String source,
                                String destination,
                                Double revenue,
                                Double totalExpense,
                                Double profit) {
        this.tripId = tripId;
        this.vehicleNumber = vehicleNumber;
        this.source = source;
        this.destination = destination;
        this.revenue = revenue;
        this.totalExpense = totalExpense;
        this.profit = profit;
    }

    public Long getTripId() { return tripId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public Double getRevenue() { return revenue; }
    public Double getTotalExpense() { return totalExpense; }
    public Double getProfit() { return profit; }
}