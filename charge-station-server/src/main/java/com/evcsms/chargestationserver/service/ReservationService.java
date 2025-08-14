package com.evcsms.chargestationserver.service;

import com.evcsms.chargestationserver.dto.CreateReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

    Long createReservation(CreateReservationDTO dto);

    Page<CreateReservationDTO> getAllReservations(Pageable pageable);

    CreateReservationDTO getReservationById(Long id);

    void deleteReservation(Long id);

    void updateReservation(Long id, CreateReservationDTO dto);
}
