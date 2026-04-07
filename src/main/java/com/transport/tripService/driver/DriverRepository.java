package com.transport.tripService.driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByPhone(String phone);

    boolean existsByPhone(String phone);

    boolean existsByLicenseNumber(String licenseNumber);
}