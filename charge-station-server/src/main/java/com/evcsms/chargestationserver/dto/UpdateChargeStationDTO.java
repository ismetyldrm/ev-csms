package com.evcsms.chargestationserver.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UpdateChargeStationDTO {
    private String name;

    private String type;

    private Integer parkingSlots;

    private String country;

    private String city;

    private String state;

    private String district;

    private String postcode;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private List<@NotNull UpdateChargePointDTO> chargePoints;

    public UpdateChargePointDTO findChargePoint(String ocppId) {
        return chargePoints.stream()
                .filter(chargePoint -> chargePoint.getOcppId().equals(ocppId))
                .findFirst()
                .orElse(null);
    }
}
