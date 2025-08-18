package com.evcsms.chargestationserver.api.controller;

import com.evcsms.chargestationserver.dto.CreateChargeStationDTO;
import com.evcsms.chargestationserver.dto.UpdateChargeStationDTO;
import com.evcsms.chargestationserver.service.ChargeStationService;
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
@RequestMapping("/api/v1/charge-stations")
public class ChargeStationController {

    private final ChargeStationService chargeStationService;

    @PostMapping
    @Operation(
            summary = "Create a new charge station",
            description = "Adds a new charge station to the system",
            parameters = {
                    @Parameter(
                            name = "CreateChargeStationDTO",
                            description = "Details of the charge station to be created",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Charge station created successfully")
            }
    )
    public ResponseEntity<Void> create(@Validated @RequestBody CreateChargeStationDTO dto) {
        Long id = chargeStationService.createChargeStation(dto);
        URI location = URI.create("/api/v1/charge-stations/" + id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing charge station",
            description = "Updates the details of an existing charge station",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the charge station to be updated",
                            required = true
                    ),
                    @Parameter(
                            name = "UpdateChargeStationDTO",
                            description = "Details of the charge station to be updated",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Charge station updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Charge station not found")
            }
    )
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Validated @RequestBody UpdateChargeStationDTO dto) {
        chargeStationService.updateChargeStation(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "Get all charge stations",
            description = "Retrieves a paginated list of all charge stations",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number to retrieve",
                            required = false,
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "Number of charge stations per page",
                            required = false,
                            example = "10"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Charge stations retrieved successfully")
            }
    )
    public ResponseEntity<Page<CreateChargeStationDTO>> getAll(Pageable pageable) {
        Page<CreateChargeStationDTO> chargeStations = chargeStationService.getAllChargeStations(pageable);
        return ResponseEntity.ok(chargeStations);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a charge station by ID",
            description = "Retrieves a charge station by its ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the charge station to retrieve",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Charge station retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Charge station not found")
            }
    )
    public ResponseEntity<CreateChargeStationDTO> getById(@PathVariable Long id) {
        CreateChargeStationDTO dto = chargeStationService.getChargeStationById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a charge station",
            description = "Deletes a charge station by its ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the charge station to delete",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Charge station deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Charge station not found")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargeStationService.deleteChargeStation(id);
        return ResponseEntity.ok().build();
    }
}