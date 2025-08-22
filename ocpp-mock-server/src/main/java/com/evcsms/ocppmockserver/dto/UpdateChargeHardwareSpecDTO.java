package com.evcsms.ocppmockserver.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateChargeHardwareSpecDTO {
    private String chargePointVendor;

    private String chargePointModel;

    private String chargePointSerialNumber;

    private String chargeBoxSerialNumber;

    private String firmwareVersion;

    private String iccid;

    private String imsi;

    private String meterType;

    private String meterSerialNumber;

    private List<CreateChargePointDTO> chargePoints;
}
