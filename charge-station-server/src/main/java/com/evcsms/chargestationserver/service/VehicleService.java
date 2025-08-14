package com.evcsms.chargestationserver.service;

import com.evcsms.chargestationserver.dto.CreateChargeStationDTO;
import com.evcsms.chargestationserver.dto.CreateVehicleDTO;
import com.evcsms.chargestationserver.dto.UpdateVehicleDTO;
import com.evcsms.chargestationserver.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {

    Long createVehicle(CreateVehicleDTO dto);

    Page<CreateVehicleDTO> getAllVehicles(Pageable pageable);

    CreateVehicleDTO getVehicleById(Long id);

    void deleteVehicle(Long id);

    Vehicle updateVehicle(Long id, UpdateVehicleDTO dto);
}
