package com.evcsms.chargestationserver.ocpp;

import com.evcsms.chargestationserver.model.ChargeHardwareSpec;
import com.evcsms.chargestationserver.model.ChargePoint;
import com.evcsms.chargestationserver.model.Connector;
import com.evcsms.chargestationserver.model.ConnectorStatusType;
import com.evcsms.chargestationserver.repository.ChargeHardwareSpecRepository;
import com.evcsms.chargestationserver.repository.ChargePointRepository;
import com.evcsms.chargestationserver.repository.ConnectorRepository;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.model.core.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class MyJsonServerCoreEventHandler implements ServerCoreEventHandler {
    private final Map<UUID, String> connections;
    private final ChargePointRepository chargePointRepository;
    private final ChargeHardwareSpecRepository hardwareSpecRepository;
    private final ConnectorRepository connectorRepository;

    @Override
    public AuthorizeConfirmation handleAuthorizeRequest(UUID sessionIndex, AuthorizeRequest request) {
        // TODO implement authorization logic

        log.info(String.valueOf(request));
        // ... handle event
        IdTagInfo idTagInfo = new IdTagInfo();
        idTagInfo.setExpiryDate(ZonedDateTime.now());
        idTagInfo.setParentIdTag("test");
        idTagInfo.setStatus(AuthorizationStatus.Accepted);

        return new AuthorizeConfirmation(idTagInfo);
    }

    @Transactional
    @Override
    public BootNotificationConfirmation handleBootNotificationRequest(UUID sessionIndex, BootNotificationRequest request) {
        log.info("Handling BootNotificationRequest: {}", request);
        // ... handle event
        // TODO 1: hardware_spec tablosu oluşturalım, gelen specleri ilgili chargePointein specine yazalım
        final String ocppId = connections.get(sessionIndex);
        chargePointRepository.findByOcppId(ocppId)
                .ifPresentOrElse(
                        chargePoint -> {

                            ChargeHardwareSpec spec = chargePoint.getHardwareSpec();
                            if(spec == null) {
                                spec = new ChargeHardwareSpec();
                            }
                            spec.setChargePointVendor(request.getChargePointVendor());
                            spec.setChargePointModel(request.getChargePointModel());
                            spec.setChargePointSerialNumber(request.getChargePointSerialNumber());
                            spec.setFirmwareVersion(request.getFirmwareVersion());
                            spec.setIccid(request.getIccid());
                            spec.setImsi(request.getImsi());
                            spec.setMeterType(request.getMeterType());
                            spec.setMeterSerialNumber(request.getMeterSerialNumber());
                            spec.setChargeBoxSerialNumber(request.getChargeBoxSerialNumber());
                            spec.setChargePoint(chargePoint);
                            chargePoint.setHardwareSpec(spec);

                            chargePointRepository.save(chargePoint);
                        },
                        () -> {
                            System.out.println("Charge point not found for OCPP ID: " + ocppId);
                        }
                );
        System.out.println(">>> BootNotification received, vendor=" + request.getChargePointVendor());



        return new BootNotificationConfirmation(ZonedDateTime.now(), 30, RegistrationStatus.Accepted); // returning null means unsupported feature
    }

    @Override
    public DataTransferConfirmation handleDataTransferRequest(UUID sessionIndex, DataTransferRequest request) {

        System.out.println(request);
        // ... handle event

        return null; // returning null means unsupported feature
    }

    @Transactional
    @Override
    public HeartbeatConfirmation handleHeartbeatRequest(UUID sessionIndex, HeartbeatRequest request) {

        System.out.println(">>> HeartBeat received: {}" + request);
        // ... handle event
        final String ocppId = connections.get(sessionIndex);

        // TODO: FakeJsonClient dan gelen Heartbeat request'ini chargePointRepository'e kaydedelim
       log.info(">>> sessionIndex: {}, ocppId: {}", sessionIndex, ocppId);
        chargePointRepository.findByOcppId(ocppId)
                .ifPresentOrElse(chargePoint -> {
                    chargePoint.setLastHealthChecked(ZonedDateTime.now().toLocalDateTime());

                    chargePointRepository.save(chargePoint);

                }, () -> System.out.println("Charge point not found for OCPP ID: " + ocppId));


        return new HeartbeatConfirmation(ZonedDateTime.now());


    }

    @Override
    public MeterValuesConfirmation handleMeterValuesRequest(UUID sessionIndex, MeterValuesRequest request) {

        System.out.println(request);
        // ... handle event

        return null; // returning null means unsupported feature
    }

    @Override
    public StartTransactionConfirmation handleStartTransactionRequest(UUID sessionIndex, StartTransactionRequest request) {

        System.out.println(request);
        // ... handle event

        return null; // returning null means unsupported feature
    }

    @Override
    public StatusNotificationConfirmation handleStatusNotificationRequest(UUID sessionIndex, StatusNotificationRequest request) {

        System.out.println("Received StatusNotification: " + request);

        final String ocppId = connections.get(sessionIndex);
        Optional<ChargePoint> optionalChargePoint = chargePointRepository.findByOcppId(ocppId);

        if (optionalChargePoint.isPresent()) {
            ChargePoint chargePoint = optionalChargePoint.get();
            Integer connectorIndex = request.getConnectorId();

            Optional<Connector> optionalConnector = connectorRepository.findByChargePointAndIndex(chargePoint, connectorIndex);

            optionalConnector.ifPresentOrElse(connector -> {

                connector.setStatus(ConnectorStatusType.valueOf(request.getStatus().name()));
                connectorRepository.save(connector);
                System.out.println("Updated connector " + connector.getIndex() + " with status " + connector.getStatus());
            }, () -> {

                Connector newConnector = new Connector();
                newConnector.setIndex(connectorIndex);
                newConnector.setChargePoint(chargePoint);
                newConnector.setStatus(ConnectorStatusType.valueOf(request.getStatus().name()));
                connectorRepository.save(newConnector);
                System.out.println("Created new connector with index " + newConnector.getIndex());
            });

        } else {
            System.err.println("ChargePoint not found for sessionIndex: " + sessionIndex);
        }

        return new StatusNotificationConfirmation();
    }

    @Override
    public StopTransactionConfirmation handleStopTransactionRequest(UUID sessionIndex, StopTransactionRequest request) {

        System.out.println(request);
        // ... handle event

        return null; // returning null means unsupported feature
    }
}
