package com.evcsms.ocppmockserver.repository;

import com.evcsms.ocppmockserver.model.MockChargePoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MockChargePointRepository extends JpaRepository<MockChargePoint, Long> {
    Optional<MockChargePoint> findByOcppId(String ocppId);
}
