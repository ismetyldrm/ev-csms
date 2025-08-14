package com.evcsms.chargestationserver.api.controller;

import com.evcsms.chargestationserver.dto.CreateReservationDTO;
import com.evcsms.chargestationserver.dto.CreateVehicleDTO;
import com.evcsms.chargestationserver.service.ReservationService;
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
    public ResponseEntity<Void> create(@Validated @RequestBody CreateReservationDTO dto)
    {
        Long id = reservationService.createReservation(dto);
        URI location = URI.create("/api/v1/reservations/" + id);
        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    public ResponseEntity<Page<CreateReservationDTO>> getAll(Pageable pageable) {
        Page<CreateReservationDTO> reservations = reservationService.getAllReservations(pageable);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateReservationDTO> getById(@PathVariable Long id) {
        CreateReservationDTO dto = reservationService.getReservationById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Validated @RequestBody CreateReservationDTO dto) {
        reservationService.updateReservation(id, dto);
        return ResponseEntity.ok().build();
    }
}
