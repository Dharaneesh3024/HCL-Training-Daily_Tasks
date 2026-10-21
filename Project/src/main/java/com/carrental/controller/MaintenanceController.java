package com.carrental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.carrental.dto.MaintenanceRequest;
import com.carrental.model.Maintenance;
import com.carrental.service.MaintenanceService;

import jakarta.validation.Valid;

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
    
    @GetMapping()
    public List<Maintenance> getAllMaintenance(){
    	return maintenanceService.getAllMaintenance();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenance(@PathVariable int id){
    	Maintenance requestedMaintenance=maintenanceService.getMaintenance(id);
    	return ResponseEntity.ok(requestedMaintenance);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMaintenance(@PathVariable int id){
    	maintenanceService.removeMaintenance(id);
    	return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    	}
    
    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> updateMaintenance(@PathVariable int id,@Valid @RequestBody MaintenanceRequest request){
    	Maintenance maintenance=maintenanceService.updateMaintenance(id, request);
    	return ResponseEntity.ok(maintenance);
    }
}