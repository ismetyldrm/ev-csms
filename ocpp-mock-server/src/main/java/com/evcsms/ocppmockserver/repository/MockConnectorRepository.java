package com.evcsms.ocppmockserver.repository;

import com.evcsms.ocppmockserver.model.Connector;
import com.evcsms.ocppmockserver.model.MockChargePoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MockConnectorRepository extends JpaRepository<Connector, Long> {

    List<Connector> findByMockChargePoint(MockChargePoint chargePoint);

}
