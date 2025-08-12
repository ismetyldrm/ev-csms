package com.evcsms.chargestationserver.service;

import com.evcsms.chargestationserver.dto.CreateVehicleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {

    Long createVehicle(CreateVehicleDTO dto);

    Page<CreateVehicleDTO> getAllVehicles(Pageable pageable);

    CreateVehicleDTO getVehicleById(Long id);

    void deleteVehicle(Long id);
}
