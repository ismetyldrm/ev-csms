package com.evcsms.chargestationserver.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateVehicleDTO {


    private Integer userId;

    private String registrationPlate;

    private String vehicleIdentificationNumber;

    private String brand;

    private String model;

    private Integer version;

    private LocalDateTime created;

    private LocalDateTime updated;



}
