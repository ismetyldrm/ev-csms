package com.evcsms.ocppmockserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mock_charge_hardware_spec")
public class MockChargeHardwareSpec extends TimestampedEntity{


    @Column(name = "charge_point_vendor")
    private String chargePointVendor;

    @Column(name = "charge_point_model")
    private String chargePointModel;

    @Column(name = "charge_point_serial_number")
    private String chargePointSerialNumber;

    @Column(name = "charge_box_serial_number")
    private String chargeBoxSerialNumber;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Column(name = "iccid")
    private String iccid;

    @Column(name = "imsi")
    private String imsi;

    @Column(name = "meter_type")
    private String meterType;

    @Column(name = "meter_serial_number")
    private String meterSerialNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mock_charge_point_id")
    private MockChargePoint mockChargePoint;

}
