package com.evcsms.chargestationserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateVehicleDTO {

    private Integer userId;

    private String registrationPlate;

    private String vehicleIdentificationNumber;

    private String brand;

    private String model;

    private Integer version;

    private LocalDateTime created;

    private LocalDateTime updated;

}
