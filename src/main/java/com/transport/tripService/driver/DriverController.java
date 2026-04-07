package com.transport.tripService.driver;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(
            @Valid @RequestBody CreateDriverRequest request) {
        Driver driver = driverService.createDriver(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Driver> getDriverByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(driverService.getDriverByPhone(phone));
    }
}