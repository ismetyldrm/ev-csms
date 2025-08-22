package com.evcsms.ocppmockserver.ocpp;


import com.evcsms.ocppmockserver.model.MockChargePoint;
import com.evcsms.ocppmockserver.model.Connector;
import com.evcsms.ocppmockserver.model.MockChargePoint;
import com.evcsms.ocppmockserver.model.MockChargeHardwareSpec;
import com.evcsms.ocppmockserver.repository.MockChargePointRepository;
import com.evcsms.ocppmockserver.repository.MockConnectorRepository;
import eu.chargetime.ocpp.IClientAPI;
import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.model.core.*;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FakeJsonClient {
    private final MockChargePointRepository mockChargePointRepository;
    private final MockConnectorRepository mockConnectorRepository;

    private String ocppId;
    private IClientAPI client;
    private ClientCoreProfile core;

    public FakeJsonClient(MockChargePointRepository mockChargePointRepository, MockConnectorRepository mockconnectorRepository) {
        this.mockChargePointRepository = mockChargePointRepository;
        this.mockConnectorRepository = mockconnectorRepository;
    }

    public void connect(String ocppId) {
        this.ocppId = ocppId;
        // The core profile is mandatory
        core = new ClientCoreProfile(new ClientCoreEventHandler() {
            @Override
            public ChangeAvailabilityConfirmation handleChangeAvailabilityRequest(ChangeAvailabilityRequest request) {

                System.out.println(request);
                // ... handle event

                return new ChangeAvailabilityConfirmation(AvailabilityStatus.Accepted);
            }

            @Override
            public GetConfigurationConfirmation handleGetConfigurationRequest(GetConfigurationRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public ChangeConfigurationConfirmation handleChangeConfigurationRequest(ChangeConfigurationRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public ClearCacheConfirmation handleClearCacheRequest(ClearCacheRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public DataTransferConfirmation handleDataTransferRequest(DataTransferRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public RemoteStartTransactionConfirmation handleRemoteStartTransactionRequest(RemoteStartTransactionRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public RemoteStopTransactionConfirmation handleRemoteStopTransactionRequest(RemoteStopTransactionRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public ResetConfirmation handleResetRequest(ResetRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public UnlockConnectorConfirmation handleUnlockConnectorRequest(UnlockConnectorRequest request) {

                System.out.println(request);
                // ... handle event

                return new UnlockConnectorConfirmation(UnlockStatus.Unlocked); // returning null means unsupported feature
            }
        });
        client = new JSONClient(core);
        client.connect("ws://localhost:12000/" + ocppId, null);
    }

    public void sendBootNotification() throws Exception {
        Optional<MockChargePoint> optionalChargePoint = mockChargePointRepository.findByOcppId(ocppId);
        if (optionalChargePoint.isPresent()) {
            final MockChargePoint mockChargePoint = optionalChargePoint.get();
            final MockChargeHardwareSpec hardwareSpec = mockChargePoint.getHardwareSpec();
            final BootNotificationRequest request = core.createBootNotificationRequest(hardwareSpec.getChargePointVendor(), hardwareSpec.getChargePointModel());
            // TODO set rest params

            request.setImsi(hardwareSpec.getImsi());
            request.setFirmwareVersion(hardwareSpec.getFirmwareVersion());
            request.setIccid(hardwareSpec.getIccid());
            request.setChargePointSerialNumber(hardwareSpec.getChargePointSerialNumber());
            request.setChargePointModel(hardwareSpec.getChargePointModel());
            request.setMeterType(hardwareSpec.getMeterType());
            request.setMeterSerialNumber(hardwareSpec.getMeterSerialNumber());
            request.setChargeBoxSerialNumber(hardwareSpec.getChargeBoxSerialNumber());


            System.out.println(">>> Sending BootNotification: " + request);
            client.send(request).whenComplete((s, ex) -> System.out.println(s));
        }
    }

    @Transactional
    @Scheduled(initialDelay = 20000,fixedDelay = 30000)
    public void sendStatusNotification() throws OccurenceConstraintException, UnsupportedFeatureException {
        List<MockChargePoint> chargePoints = mockChargePointRepository.findAll();

        for (MockChargePoint mockChargePoint : chargePoints) {

            connect(mockChargePoint.getOcppId());

            Optional<MockChargePoint> optionalChargePoint = mockChargePointRepository.findByOcppId(ocppId);
            if (optionalChargePoint.isEmpty()) continue;
            MockChargePoint chargePoint = optionalChargePoint.get();

            List<Connector> connectors = mockConnectorRepository.findByMockChargePoint(chargePoint);

            for (Connector connector : connectors) {
                ChargePointStatus statusToSend = connector.getStatus() != null
                        ? ChargePointStatus.valueOf(connector.getStatus().name())
                        : null;
                StatusNotificationRequest request = core.createStatusNotificationRequest(
                        connector.getIndex(),
                        ChargePointErrorCode.NoError,
                        statusToSend
                );
                request.setTimestamp(ZonedDateTime.now());

                System.out.println(">>> Sending StatusNotification for connector " + connector.getIndex() + ": " + request);

                client.send(request).whenComplete((s, ex) -> {
                    if (ex != null) {
                        System.err.println("Error sending StatusNotification: " + ex.getMessage());
                    } else {
                        System.out.println(">>> StatusNotificationConfirmation received: " + s);
                    }
                });
            }
        }
    }
    public void disconnect() {
        client.disconnect();
    }

    @Transactional
    @Scheduled(initialDelay = 20000, fixedDelay = 30000)
    public void sendHeartbeat() throws OccurenceConstraintException, UnsupportedFeatureException, InterruptedException {
        List<MockChargePoint> chargePoints = mockChargePointRepository.findAll();

    for(MockChargePoint mockChargePoint : chargePoints){
        Thread.sleep(1000);
        connect(mockChargePoint.getOcppId());
        HeartbeatRequest request = core.createHeartbeatRequest();
        System.out.println(">>> Sending Heartbeat: " + request);
        client.send(request).whenComplete((s, ex) -> {
            if (ex != null) {
                System.err.println("Error sending Heartbeat: " + ex.getMessage());
            } else {
                System.out.println(">>> HeartbeatConfirmation received: " + s);
            }
        });
    }

    }
}
