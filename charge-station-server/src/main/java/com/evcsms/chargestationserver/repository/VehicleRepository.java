package com.evcsms.chargestationserver.repository;

import com.evcsms.chargestationserver.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findById(Long id);
}
