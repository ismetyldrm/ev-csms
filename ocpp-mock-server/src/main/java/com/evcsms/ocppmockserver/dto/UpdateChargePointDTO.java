package com.evcsms.ocppmockserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateChargePointDTO {

    private String ocppId;

    private Boolean disabled;

    private UpdateChargeHardwareSpecDTO hardwareSpec;

    private List<UpdateConnectorDTO> connectors;

    public UpdateChargeHardwareSpecDTO getHardwareSpec() {
        if (this.hardwareSpec == null) {
            throw new IllegalStateException("HardwareSpec is missing in UpdateChargePointDTO");
        }
        return hardwareSpec;
    }

    public List<UpdateConnectorDTO> getConnectors() {
        return connectors == null ? List.of() : connectors;
    }


}
