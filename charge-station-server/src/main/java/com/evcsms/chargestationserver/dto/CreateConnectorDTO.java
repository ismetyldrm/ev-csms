package com.evcsms.chargestationserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class CreateConnectorDTO {

    @NotNull
    private Integer index;

    @NotBlank
    private String currentType;

    @NotNull
    private BigDecimal powerFactor;

    @NotNull
    private String status;


}
