package com.transport.tripService.trip;

public class TripResponse {

    private Long id;
    private String vehicleNumber;
    private String source;
    private String destination;
    private Double revenue;
    private String status;

    public TripResponse(Long id,
                        String vehicleNumber,
                        String source,
                        String destination,
                        Double revenue,
                        String status) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.source = source;
        this.destination = destination;
        this.revenue = revenue;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public Double getRevenue() { return revenue; }
    public String getStatus() { return status; }
}