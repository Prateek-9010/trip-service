package com.transport.tripService.vehicle;

import jakarta.persistence.*;

@Entity
@Table(
    name = "vehicles",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "vehicle_number")
    }
)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    // capacity in tons
    @Column(nullable = false)
    private Double capacity;

    @Column(nullable = false)
    private Boolean active = true;

    protected Vehicle() {
        // JPA requires a default constructor
    }

    public Vehicle(String vehicleNumber, String ownerName, Double capacity) {
        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.capacity = capacity;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Double getCapacity() {
        return capacity;
    }

    public Boolean getActive() {
        return active;
    }
}