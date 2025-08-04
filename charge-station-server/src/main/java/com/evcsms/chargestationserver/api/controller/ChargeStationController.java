package com.evcsms.chargestationserver.api.controller;

import com.evcsms.chargestationserver.dto.CreateChargeStationDTO;
import com.evcsms.chargestationserver.dto.UpdateChargeStationDTO;
import com.evcsms.chargestationserver.service.ChargeStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/charge-stations")
public class ChargeStationController {

    private final ChargeStationService chargeStationService;

    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody CreateChargeStationDTO dto) {
        Long id = chargeStationService.createChargeStation(dto);
        URI location = URI.create("/api/v1/charge-stations/" + id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Validated @RequestBody UpdateChargeStationDTO dto) {
        chargeStationService.updateChargeStation(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<CreateChargeStationDTO>> getAll(Pageable pageable) {
        Page<CreateChargeStationDTO> chargeStations = chargeStationService.getAllChargeStations(pageable);
        return ResponseEntity.ok(chargeStations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateChargeStationDTO> getById(@PathVariable Long id) {
        CreateChargeStationDTO dto = chargeStationService.getChargeStationById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargeStationService.deleteChargeStation(id);
        return ResponseEntity.ok().build();
    }
}