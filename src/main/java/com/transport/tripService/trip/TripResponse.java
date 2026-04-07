package com.transport.tripService.trip;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TripResponse {

    private Long id;
    private String fromLocation;
    private String toLocation;
    private String status;
    private BigDecimal fareAmount;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long vehicleId;
    private String vehicleNumber;
    private Long driverId;
    private String driverName;
    private String driverPhone;

    // === No-arg constructor ===
    public TripResponse() {}

    // === Static factory: entity → DTO ===
    public static TripResponse fromEntity(Trip trip) {
        TripResponse r = new TripResponse();
        r.setId(trip.getId());
        r.setFromLocation(trip.getFromLocation());
        r.setToLocation(trip.getToLocation());
        r.setStatus(trip.getStatus().name());
        r.setFareAmount(trip.getFareAmount());
        r.setCreatedAt(trip.getCreatedAt());
        r.setStartedAt(trip.getStartedAt());
        r.setCompletedAt(trip.getCompletedAt());

        if (trip.getVehicle() != null) {
            r.setVehicleId(trip.getVehicle().getId());
            r.setVehicleNumber(trip.getVehicle().getRegistrationNumber());
        }

        if (trip.getDriver() != null) {
            r.setDriverId(trip.getDriver().getId());
            r.setDriverName(trip.getDriver().getName());
            r.setDriverPhone(trip.getDriver().getPhone());
        }

        return r;
    }

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }
}