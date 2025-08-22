package com.evcsms.chargestationserver.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "charge_hardware_spec")
public class ChargeHardwareSpec extends TimestampedEntity{
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

    @OneToOne
    @JoinColumn(name = "charge_point_id")
    private ChargePoint chargePoint;

}
