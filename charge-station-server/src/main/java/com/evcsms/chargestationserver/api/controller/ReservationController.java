package com.evcsms.chargestationserver.api.controller;

import com.evcsms.chargestationserver.dto.CreateReservationDTO;
import com.evcsms.chargestationserver.service.ReservationService;
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
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(
            summary = "Create a new reservation",
            description = "Adds a new reservation to the system",
            parameters = {
                    @Parameter(
                            name = "CreateReservationDTO",
                            description = "Details of the reservation to be created",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reservation created successfully")
            }
    )
    public ResponseEntity<Void> create(@Validated @RequestBody CreateReservationDTO dto)
    {
        Long id = reservationService.createReservation(dto);
        URI location = URI.create("/api/v1/reservations/" + id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    @Operation(
            summary = "Get all reservations",
            description = "Returns a paginated list of all reservations",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number to retrieve",
                            required = false,
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "Number of reservations per page",
                            required = false,
                            example = "10"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of reservations retrieved successfully")
            }
    )
    public ResponseEntity<Page<CreateReservationDTO>> getAll(Pageable pageable) {
        Page<CreateReservationDTO> reservations = reservationService.getAllReservations(pageable);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get reservation by ID",
            description = "Returns a reservation based on the provided ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the reservation to retrieve",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    public ResponseEntity<CreateReservationDTO> getById(@PathVariable Long id) {
        CreateReservationDTO dto = reservationService.getReservationById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a reservation",
            description = "Deletes a reservation by its ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the reservation to delete",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a reservation",
            description = "Updates an existing reservation by ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the reservation to update",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Reservation not found")
            }
    )
    public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody CreateReservationDTO dto) {
        reservationService.updateReservation(id, dto);
        return ResponseEntity.ok().build();
    }
}
