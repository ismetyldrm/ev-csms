package com.evcsms.chargestationserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateReservationDTO {


    private Long connectorId;

    private Long userId;

    private Long vehicleId;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private String status;



}
