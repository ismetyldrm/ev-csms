package com.evcsms.chargestationserver.repository;

import com.evcsms.chargestationserver.model.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {
}
