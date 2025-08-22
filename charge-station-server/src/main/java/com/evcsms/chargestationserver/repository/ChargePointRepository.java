package com.evcsms.chargestationserver.repository;

import com.evcsms.chargestationserver.model.ChargePoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargePointRepository extends JpaRepository<ChargePoint, Long> {
    Optional<ChargePoint> findByOcppId(String ocppId);
}
