package com.evcsms.chargestationserver.ocpp;


import com.evcsms.chargestationserver.model.ChargePoint;
import com.evcsms.chargestationserver.repository.ChargeHardwareSpecRepository;
import com.evcsms.chargestationserver.repository.ChargePointRepository;
import com.evcsms.chargestationserver.repository.ConnectorRepository;
import eu.chargetime.ocpp.AuthenticationException;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.SessionInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class MyJsonServer {
    private final Map<UUID, String> connections = new HashMap<>();
    private final ChargePointRepository chargePointRepository;
    private final ConnectorRepository connectorRepository;
    private ServerCoreProfile core;
    private final ChargeHardwareSpecRepository hardwareSpecRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void startServer() throws Exception {

        // hatalı ! sonra düzelt
        core = new ServerCoreProfile(new MyJsonServerCoreEventHandler(connections, chargePointRepository, hardwareSpecRepository,connectorRepository));

        JSONServer server = new JSONServer(core);

        server.open("localhost", 12000, new ServerEvents() {
            @Override
            public void authenticateSession(SessionInformation information, String username, byte[] password) throws AuthenticationException {
                System.out.println("New session authentication with " + username + ": " + information.getIdentifier());
            }

            @Override
            public void newSession(UUID sessionIndex, SessionInformation information) {
                final String ocppId = information.getIdentifier().substring(1);
                Optional<ChargePoint> chargePointOptional = chargePointRepository.findByOcppId(ocppId);
                if(chargePointOptional.isPresent()) {
                    ChargePoint chargePoint = chargePointOptional.get();
                    chargePoint.setOnline(true);
                    chargePoint.setLastConnected(LocalDateTime.now());
                    chargePointRepository.save(chargePoint);
                }


                // sessionIndex is used to send messages.
                System.out.println("New session " + sessionIndex + ": " + information.getIdentifier());
                connections.put(sessionIndex, ocppId);
            }

            @Override
            public void lostSession(UUID sessionIndex) {
                final String ocppId = connections.get(sessionIndex);
                System.out.println("Session " + sessionIndex + " lost connection");
                chargePointRepository.findByOcppId(ocppId).ifPresent(chargePoint -> {
                    chargePoint.setOnline(false);
                    chargePoint.setLastConnected(LocalDateTime.now());
                    chargePointRepository.save(chargePoint);
                });
                connections.remove(sessionIndex);
            }
        });
    }
}
