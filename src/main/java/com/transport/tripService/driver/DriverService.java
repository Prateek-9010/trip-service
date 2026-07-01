package com.transport.tripService.driver;

import com.transport.tripService.common.BadRequestException;
import com.transport.tripService.common.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public DriverResponse createDriver(CreateDriverRequest request) {
        if (driverRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException(
                    "Driver with phone " + request.getPhone() + " already exists");
        }

        if (request.getLicenseNumber() != null &&
                driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new BadRequestException(
                    "Driver with license " + request.getLicenseNumber() + " already exists");
        }

        Driver driver = new Driver();
        driver.setName(request.getName());
        driver.setPhone(request.getPhone());
        driver.setLicenseNumber(request.getLicenseNumber());

        Driver saved = driverRepository.save(driver);
        return DriverResponse.fromEntity(saved);
    }

    public List<DriverResponse> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(DriverResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public DriverResponse getDriverResponseById(Long id) {
        Driver driver = getDriverById(id);
        return DriverResponse.fromEntity(driver);
    }

    public DriverResponse getDriverResponseByPhone(String phone) {
        Driver driver = getDriverByPhone(phone);
        return DriverResponse.fromEntity(driver);
    }

    // === INTERNAL: Used by other services (TripService, ExpenseService) ===
    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with id: " + id));
    }

    public Driver getDriverByPhone(String phone) {
        return driverRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with phone: " + phone));
    }
}