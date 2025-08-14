package com.evcsms.chargestationserver.service.Impl;

import com.evcsms.chargestationserver.dto.CreateReservationDTO;
import com.evcsms.chargestationserver.mapper.ReservationMapper;
import com.evcsms.chargestationserver.model.Reservation;
import com.evcsms.chargestationserver.model.ReservationStatusType;
import com.evcsms.chargestationserver.repository.ReservationRepository;
import com.evcsms.chargestationserver.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Long createReservation(CreateReservationDTO dto){
        Reservation reservation = reservationMapper.toReservation(dto);
        Reservation newReservation = reservationRepository.save(reservation);
        return newReservation.getId();
    }

    public Page<CreateReservationDTO> getAllReservations(Pageable pageable){
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::toCreateReservationDTO);
    }

    public CreateReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation with id " + id + " does not exist"));
        return reservationMapper.toCreateReservationDTO(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation with id " + id + " does not exist"));
        reservationRepository.delete(reservation);
    }

    public void updateReservation(Long id, CreateReservationDTO dto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation with id " + id + " does not exist"));
        reservationMapper.toReservation(reservation, dto);
        if (reservation.getStatus() == null) {
            reservation.setStatus(ReservationStatusType.ACTIVE);
        }
        reservationRepository.save(reservation);
    }
}
