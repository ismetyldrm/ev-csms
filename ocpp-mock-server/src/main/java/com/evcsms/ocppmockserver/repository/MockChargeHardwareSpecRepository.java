package com.evcsms.ocppmockserver.repository;

import com.evcsms.ocppmockserver.model.MockChargeHardwareSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockChargeHardwareSpecRepository extends JpaRepository<MockChargeHardwareSpec, Long> {
}
