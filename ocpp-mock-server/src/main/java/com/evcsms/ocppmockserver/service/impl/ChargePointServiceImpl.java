package com.evcsms.ocppmockserver.service.impl;

import com.evcsms.ocppmockserver.dto.CreateChargePointDTO;
import com.evcsms.ocppmockserver.dto.CreateConnectorDTO;
import com.evcsms.ocppmockserver.dto.UpdateChargePointDTO;
import com.evcsms.ocppmockserver.dto.UpdateConnectorDTO;
import com.evcsms.ocppmockserver.mapper.ChargePointMapper;
import com.evcsms.ocppmockserver.model.MockChargePoint;
import com.evcsms.ocppmockserver.model.Connector;
import com.evcsms.ocppmockserver.model.MockChargeHardwareSpec;
import com.evcsms.ocppmockserver.ocpp.FakeJsonClient;
import com.evcsms.ocppmockserver.repository.MockChargePointRepository;
import com.evcsms.ocppmockserver.service.ChargePointService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargePointServiceImpl implements ChargePointService {
    private final MockChargePointRepository mockChargePointRepository;
    private final ChargePointMapper chargePointMapper;
    private final FakeJsonClient fakeJsonClient;

    @EventListener(ApplicationReadyEvent.class)
    public void
    doSomethingAfterStartup() {
        mockChargePointRepository.findAll().forEach(chargePoint -> {
            fakeJsonClient.connect(chargePoint.getOcppId());
            try {
                fakeJsonClient.sendBootNotification();
                fakeJsonClient.sendStatusNotification();
            } catch (Exception e) {
                log.error("Unable to connect to charge point with OCPP ID: {}. Error: {}", chargePoint.getOcppId(), e.getMessage());
            }
        });
    }

    @Transactional
    public Long create(CreateChargePointDTO chargePointDTO) {
        MockChargePoint mockChargePoint = chargePointMapper.toChargePointForCreate(chargePointDTO);

        for (CreateConnectorDTO connectorDTO : chargePointDTO.getConnectors()) {
            Connector connector = chargePointMapper.toConnectorForCreate(connectorDTO);
            mockChargePoint.addConnector(connector);
        }

        MockChargeHardwareSpec mockChargeHardwareSpec = chargePointMapper.toMockChargeHardwareSpec(chargePointDTO.getHardwareSpec());
        mockChargePoint.setHardwareSpec(mockChargeHardwareSpec);

        MockChargePoint newMockChargePoint = mockChargePointRepository.save(mockChargePoint);

        return newMockChargePoint.getId();
    }

    @Transactional
    public CreateChargePointDTO updateChargePoint(Long id, UpdateChargePointDTO dto) {
        MockChargePoint mockChargePoint = mockChargePointRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ChargePoint not found with ID: " + id));

        chargePointMapper.toChargePointForUpdate(mockChargePoint, dto);
        if (dto.getOcppId() != null) {
            mockChargePoint.setOcppId(dto.getOcppId());
        }
        if (dto.getDisabled() != null) {
            mockChargePoint.setDisabled(dto.getDisabled());
        }

        if (dto.getHardwareSpec() != null) {
            MockChargeHardwareSpec hardwareSpec = mockChargePoint.getHardwareSpec() != null
                    ? mockChargePoint.getHardwareSpec()
                    : new MockChargeHardwareSpec();

            chargePointMapper.toMockChargeHardwareSpecForUpdate(hardwareSpec, dto.getHardwareSpec());
            mockChargePoint.setHardwareSpec(hardwareSpec);
        }

        for (UpdateConnectorDTO connectorDTO : dto.getConnectors()) {
            Connector connector = mockChargePoint.findConnector(connectorDTO.getIndex())
                    .orElse(new Connector());
            chargePointMapper.toConnectorForUpdate(connector, connectorDTO);
            mockChargePoint.addConnector(connector);
        }

        mockChargePoint.removeIfNotIn(
                dto.getConnectors().stream()
                        .map(UpdateConnectorDTO::getIndex)
                        .collect(Collectors.toList())
        );

        MockChargePoint updated = mockChargePointRepository.save(mockChargePoint);
        return chargePointMapper.toDTO(updated);
    }


    public Page<CreateChargePointDTO> getAllChargePoints(Pageable pageable) {
        Page<MockChargePoint> chargeStations = mockChargePointRepository.findAll(pageable);
        return chargeStations.map(chargePointMapper::toDTO);
    }

    public CreateChargePointDTO getChargePointById(Long id) {
        MockChargePoint mockChargePoint = mockChargePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charge Station with id " + id + " does not exist"));
        return chargePointMapper.toDTO(mockChargePoint);
    }

    public void deleteChargeStation(Long id) {
        MockChargePoint mockChargePoint = mockChargePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charge Station with id " + id + " does not exist"));
        mockChargePointRepository.delete(mockChargePoint);
    }
}
