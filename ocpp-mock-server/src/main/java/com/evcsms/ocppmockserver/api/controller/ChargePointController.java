package com.evcsms.ocppmockserver.api.controller;



import com.evcsms.ocppmockserver.dto.UpdateChargePointDTO;
import com.evcsms.ocppmockserver.dto.CreateChargePointDTO;
import com.evcsms.ocppmockserver.service.ChargePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/charge-points")
public class ChargePointController {

    private final ChargePointService chargePointService;


    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody CreateChargePointDTO dto) {
        Long id = chargePointService.create(dto);
        URI location = URI.create("/api/v1/charge-points/" + id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Validated @RequestBody UpdateChargePointDTO dto) {
        chargePointService.updateChargePoint(id, dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Page<CreateChargePointDTO>> getAll(Pageable pageable) {
        Page<CreateChargePointDTO> chargeStations = chargePointService.getAllChargePoints(pageable);
        return ResponseEntity.ok(chargeStations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateChargePointDTO> getById(@PathVariable Long id) {
        CreateChargePointDTO chargePoint = chargePointService.getChargePointById(id);
        return ResponseEntity.ok(chargePoint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargePointService.deleteChargeStation(id);
        return ResponseEntity.ok().build();
    }
}
