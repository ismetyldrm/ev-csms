package com.evcsms.chargestationserver.service;

import com.evcsms.chargestationserver.dto.CreateChargeStationDTO;
import com.evcsms.chargestationserver.dto.UpdateChargeStationDTO;
import com.evcsms.chargestationserver.model.ChargeStation;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ChargeStationService {

    Long createChargeStation(CreateChargeStationDTO dto);

    CreateChargeStationDTO updateChargeStation(Long id, UpdateChargeStationDTO dto);

    Page<CreateChargeStationDTO> getAllChargeStations(Pageable pageable);

    CreateChargeStationDTO getChargeStationById(Long id);

    void deleteChargeStation(Long id);
}
