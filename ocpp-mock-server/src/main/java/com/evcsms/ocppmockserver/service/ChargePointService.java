package com.evcsms.ocppmockserver.service;

import com.evcsms.ocppmockserver.dto.CreateChargePointDTO;
import com.evcsms.ocppmockserver.dto.UpdateChargePointDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChargePointService {

    Long create(CreateChargePointDTO dto);

    Page<CreateChargePointDTO> getAllChargePoints(Pageable pageable);

    CreateChargePointDTO getChargePointById(Long id);

    void deleteChargeStation(Long id);

    CreateChargePointDTO updateChargePoint(Long id, UpdateChargePointDTO dto);

}
