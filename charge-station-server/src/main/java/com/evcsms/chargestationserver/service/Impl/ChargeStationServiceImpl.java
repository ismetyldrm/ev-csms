package com.evcsms.chargestationserver.service.Impl;

import com.evcsms.chargestationserver.dto.*;
import com.evcsms.chargestationserver.mapper.ChargeStationMapper;
import com.evcsms.chargestationserver.model.ChargePoint;
import com.evcsms.chargestationserver.model.ChargeStation;
import com.evcsms.chargestationserver.model.Connector;
import com.evcsms.chargestationserver.repository.ChargeStationRepository;
import com.evcsms.chargestationserver.service.ChargeStationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChargeStationServiceImpl implements ChargeStationService {
    private final ChargeStationRepository chargeRepository;
    private final ChargeStationMapper chargeStationMapper;


    @Transactional
    public Long createChargeStation(CreateChargeStationDTO dto) {
        ChargeStation chargeStation = chargeStationMapper.toChargeStation(dto);

        for (CreateChargePointDTO chargePointDTO : dto.getChargePoints()) {
            ChargePoint chargePoint = chargeStationMapper.toChargePointForCreate(chargePointDTO);

            for (CreateConnectorDTO connectorDTO : chargePointDTO.getConnectors()) {
                Connector connector = chargeStationMapper.toConnectorForCreate(connectorDTO);
                chargePoint.addConnector(connector);
            }

            chargeStation.addChargePoint(chargePoint);
        }

        ChargeStation newChargeStation = chargeRepository.save(chargeStation);
        return newChargeStation.getId();
    }

    @Transactional
    public CreateChargeStationDTO updateChargeStation(Long id, UpdateChargeStationDTO dto) {
        ChargeStation chargeStation = chargeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ChargeStation not found with ID: " + id));

        chargeStationMapper.toChargeStation(chargeStation, dto);

        for (UpdateChargePointDTO chargePointDTO : dto.getChargePoints()) {
            ChargePoint chargePoint = chargeStation.findChargePoint(chargePointDTO.getOcppId()).orElse(new ChargePoint());
            chargeStationMapper.toChargePointForUpdate(chargePoint, chargePointDTO);
            chargeStation.addChargePoint(chargePoint);

            for (UpdateConnectorDTO connectorDTO : chargePointDTO.getConnectors()) {
                Connector connector = chargePoint.findConnector(connectorDTO.getIndex()).orElse(new Connector());
                chargeStationMapper.toConnectorForUpdate(connector, connectorDTO);
                chargePoint.addConnector(connector);
            }

            chargePoint.removeIfNotIn(chargePointDTO.getConnectors().stream().map(UpdateConnectorDTO::getIndex).collect(Collectors.toList()));
        }

        chargeStation.removeIfNotIn(dto.getChargePoints().stream().map(UpdateChargePointDTO::getOcppId).collect(Collectors.toList()));

        ChargeStation updatedChargeStation = chargeRepository.save(chargeStation);

        return chargeStationMapper.toDTO(updatedChargeStation);
    }

    public Page<CreateChargeStationDTO> getAllChargeStations(Pageable pageable) {
        Page<ChargeStation> chargeStations = chargeRepository.findAll(pageable);
        return chargeStations.map(chargeStationMapper::toDTO);
    }

    public CreateChargeStationDTO getChargeStationById(Long id) {
        ChargeStation chargeStation = chargeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charge Station with id " + id + " does not exist"));
        return chargeStationMapper.toDTO(chargeStation);
    }

    public void deleteChargeStation(Long id) {
        ChargeStation chargeStation = chargeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charge Station with id " + id + " does not exist"));
        chargeRepository.delete(chargeStation);
    }

}
