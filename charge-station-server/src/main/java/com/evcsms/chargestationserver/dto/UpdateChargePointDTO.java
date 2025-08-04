package com.evcsms.chargestationserver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateChargePointDTO {

    private String ocppId;

    private Boolean disabled;

    private List<@NotNull UpdateConnectorDTO> connectors;

    public UpdateConnectorDTO findConnector(int index) {
        return connectors.stream()
                .filter(connector -> connector.getIndex() == index)
                .findFirst()
                .orElse(null);
    }
}
