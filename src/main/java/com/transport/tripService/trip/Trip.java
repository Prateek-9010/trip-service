package com.transport.tripService.trip;

import com.transport.tripService.vehicle.Vehicle;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private Double revenue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    protected Trip() {
    }

    public Trip(Vehicle vehicle, String source, String destination, Double revenue) {
        this.vehicle = vehicle;
        this.source = source;
        this.destination = destination;
        this.revenue = revenue;
        this.status = TripStatus.CREATED;
    }

    public Long getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Double getRevenue() {
        return revenue;
    }

    public TripStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void startTrip() {
        this.status = TripStatus.STARTED;
        this.startedAt = LocalDateTime.now();
    }

    public void completeTrip() {
        this.status = TripStatus.COMPLETED;
        this.endedAt = LocalDateTime.now();
    }
}