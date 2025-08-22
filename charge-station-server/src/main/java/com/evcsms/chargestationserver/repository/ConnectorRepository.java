package com.evcsms.chargestationserver.repository;

import com.evcsms.chargestationserver.model.ChargePoint;
import com.evcsms.chargestationserver.model.Connector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectorRepository extends JpaRepository<Connector, Long> {
    Optional<Connector> findByChargePointAndIndex(ChargePoint chargePoint, Integer connectorIndex);
}
