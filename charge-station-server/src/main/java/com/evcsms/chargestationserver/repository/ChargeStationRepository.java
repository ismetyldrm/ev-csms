package com.evcsms.chargestationserver.repository;

import com.evcsms.chargestationserver.model.ChargeStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeStationRepository extends JpaRepository<ChargeStation, Long> {

}
