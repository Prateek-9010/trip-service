package com.transport.tripService.driver;

import java.time.LocalDateTime;

public class DriverResponse {

    private Long id;
    private String name;
    private String phone;
    private String licenseNumber;
    private boolean active;
    private LocalDateTime createdAt;

    public DriverResponse() {}

    public static DriverResponse fromEntity(Driver driver) {
        DriverResponse response = new DriverResponse();
        response.setId(driver.getId());
        response.setName(driver.getName());
        response.setPhone(driver.getPhone());
        response.setLicenseNumber(driver.getLicenseNumber());
        response.setActive(driver.isActive());
        response.setCreatedAt(driver.getCreatedAt());
        return response;
    }

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}