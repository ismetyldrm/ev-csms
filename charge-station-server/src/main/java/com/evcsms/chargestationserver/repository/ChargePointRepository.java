package com.evcsms.chargestationserver.repository;

import com.evcsms.chargestationserver.model.ChargePoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargePointRepository extends JpaRepository<ChargePoint, Long> {
}
