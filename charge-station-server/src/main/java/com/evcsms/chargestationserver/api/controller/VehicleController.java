package com.evcsms.chargestationserver.api.controller;

import com.evcsms.chargestationserver.dto.CreateVehicleDTO;
import com.evcsms.chargestationserver.dto.UpdateVehicleDTO;
import com.evcsms.chargestationserver.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody CreateVehicleDTO dto) {
        Long id = vehicleService.createVehicle(dto);
        URI location = URI.create("/api/v1/vehicles/" + id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Validated @RequestBody UpdateVehicleDTO dto){
        vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<CreateVehicleDTO>> getAll(Pageable pageable) {
        Page<CreateVehicleDTO> vehicles = vehicleService.getAllVehicles(pageable);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateVehicleDTO> getById(@PathVariable Long id) {
        CreateVehicleDTO dto = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
