package com.carrental.controller;

import org.springframework.web.bind.annotation.*;

import com.carrental.dto.MaintenanceRequest;
import com.carrental.model.Maintenance;
import com.carrental.service.MaintenanceService;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public Maintenance scheduleMaintenance(
            @RequestBody MaintenanceRequest request) {
        return maintenanceService.scheduleMaintenance(request);
    }
}