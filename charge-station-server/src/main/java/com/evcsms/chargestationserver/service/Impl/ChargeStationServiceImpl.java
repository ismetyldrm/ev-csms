package com.evcsms.chargestationserver.service.Impl;

import com.evcsms.chargestationserver.dto.CreateChargeStationDTO;
import com.evcsms.chargestationserver.dto.UpdateChargeStationDTO;
import com.evcsms.chargestationserver.mapper.ChargeStationMapper;
import com.evcsms.chargestationserver.model.ChargePoint;
import com.evcsms.chargestationserver.model.ChargeStation;
import com.evcsms.chargestationserver.model.Connector;
import com.evcsms.chargestationserver.repository.ChargeStationRepository;
import com.evcsms.chargestationserver.service.ChargeStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChargeStationServiceImpl implements ChargeStationService {
    private final ChargeStationRepository chargeRepository;
    private final ChargeStationMapper chargeStationMapper;


    @Transactional
    public Long createChargeStation(CreateChargeStationDTO dto) {
        ChargeStation chargeStation = chargeStationMapper.toChargeStation(dto);
        ChargeStation newChargeStation = chargeRepository.save(chargeStation);
        return newChargeStation.getId();
    }

    @Transactional
    public List<CreateChargeStationDTO> updateChargeStation(Long id, UpdateChargeStationDTO dto) {
        ChargeStation chargeStation = chargeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Charge Station with id " + id + " does not exist"));
        chargeStationMapper.toChargeStation(chargeStation, dto);

        // TODO: Implement the logic to update charge points and connectors

        Iterator<ChargePoint> chargePointIterator = chargeStation.getChargePoints().iterator();
        while (chargePointIterator.hasNext()) {
            ChargePoint chargePoint = chargePointIterator.next();
            Optional.ofNullable(dto.findChargePoint(chargePoint.getOcppId()))
                    .ifPresentOrElse(chargePointDTO -> {
                                chargeStationMapper.toChargePointForUpdate(chargePoint, chargePointDTO);

                                Iterator<Connector> connectorIterator = chargePoint.getConnectors().iterator();
                                while (connectorIterator.hasNext()) {
                                    Connector connector = connectorIterator.next();
                                    Optional.ofNullable(chargePointDTO.findConnector(connector.getIndex()))
                                         .ifPresentOrElse(connectorDTO -> {
                                                chargeStationMapper.toConnectorForUpdate(connector, connectorDTO);
                                            }, connectorIterator::remove);
                                }
                            },
                            chargePointIterator::remove);
        }
        chargeRepository.save(chargeStation);
        return chargeStationMapper.toDTOList(chargeRepository.findAll());
    }

    public Page<CreateChargeStationDTO> getAllChargeStations(Pageable pageable){
        Page<ChargeStation> chargeStations = chargeRepository.findAll(pageable);
        return chargeStations.map(chargeStationMapper::toDTO);
    }

    public CreateChargeStationDTO getChargeStationById(Long id) {
        ChargeStation chargeStation = chargeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charge Station with id " + id + " does not exist"));
        return chargeStationMapper.toDTO(chargeStation);
    }

    public void deleteChargeStation(Long id){
        ChargeStation chargeStation = chargeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charge Station with id " + id + " does not exist"));
        chargeRepository.delete(chargeStation);
    }

}
