package com.evcsms.chargestationserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateReservationDTO {


    private Integer connectorId;

    private Integer userId;

    private Integer vehicleId;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private String status;

    private Integer version;

    private LocalDateTime created;

    private LocalDateTime updated;

}
