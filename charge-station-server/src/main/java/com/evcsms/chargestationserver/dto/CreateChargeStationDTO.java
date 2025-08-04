package com.evcsms.chargestationserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Validated
public class CreateChargeStationDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @NotNull
    private Integer parkingSlots;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String district;

    @NotBlank
    private String postcode;

    @NotBlank
    private String address;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotEmpty
    private List<@NotNull CreateChargePointDTO> chargePoints;
}
