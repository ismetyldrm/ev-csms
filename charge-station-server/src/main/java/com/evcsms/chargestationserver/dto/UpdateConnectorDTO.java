package com.evcsms.chargestationserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class UpdateConnectorDTO {

    private int index;

    private String currentType;

    private BigDecimal powerFactor;

}