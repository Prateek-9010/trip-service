package com.transport.tripService.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByVehicleId(Long vehicleId);

    List<Trip> findByDriverId(Long driverId);

    Optional<Trip> findByDriverIdAndStatus(Long driverId, TripStatus status);

    boolean existsByDriverIdAndStatus(Long driverId, TripStatus status);
}