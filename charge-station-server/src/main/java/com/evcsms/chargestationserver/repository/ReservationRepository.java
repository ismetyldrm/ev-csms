package com.evcsms.chargestationserver.repository;

import com.evcsms.chargestationserver.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
