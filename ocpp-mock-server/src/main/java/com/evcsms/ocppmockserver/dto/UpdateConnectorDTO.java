package com.evcsms.ocppmockserver.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateConnectorDTO {
    private Integer index;

    private String currentType;

    private BigDecimal powerFactor;

    private String status;
}
