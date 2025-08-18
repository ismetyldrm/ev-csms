package com.evcsms.chargestationserver.api.controller;

import com.evcsms.chargestationserver.dto.CreateVehicleDTO;
import com.evcsms.chargestationserver.dto.UpdateVehicleDTO;
import com.evcsms.chargestationserver.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Create a new vehicle",
            description = "Adds a new vehicle to the system",
            responses = {@ApiResponse(responseCode = "201", description = "Vehicle created successfully")}
    )
    public ResponseEntity<Void> create(@Validated @RequestBody CreateVehicleDTO dto) {
        Long id = vehicleService.createVehicle(dto);
        URI location = URI.create("/api/v1/vehicles/" + id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(
                    summary = "Update an existing vehicle",
                    description = "Updates the details of an existing vehicle",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "ID of the vehicle to be updated",
                                    required = true
                            )
                    },
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
                            @ApiResponse(responseCode = "404", description = "Vehicle not found")
                    }
            )
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Validated @RequestBody UpdateVehicleDTO dto){
        vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "Get all vehicles",
            description = "Retrieves a paginated list of all vehicles",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number to retrieve",
                            required = false,
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "Number of vehicles per page",
                            required = false,
                            example = "10"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully")
            }
    )
    public ResponseEntity<Page<CreateVehicleDTO>> getAll(Pageable pageable) {
        Page<CreateVehicleDTO> vehicles = vehicleService.getAllVehicles(pageable);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a vehicle by ID",
            description = "Retrieves a vehicle by its ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the vehicle to retrieve",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found")
            }
    )
    public ResponseEntity<CreateVehicleDTO> getById(@PathVariable Long id) {
        CreateVehicleDTO dto = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a vehicle",
            description = "Deletes a vehicle by its ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the vehicle to delete",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
