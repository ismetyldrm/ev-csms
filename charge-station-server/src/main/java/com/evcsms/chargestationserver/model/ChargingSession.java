package com.evcsms.chargestationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "charging_session")
public class ChargingSession extends TimestampedEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ColumnTransformer(write = "?::charging_session_status")
    private ChargingSessionStatusType status;

    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "unplug_time")
    private LocalDateTime unplugTime;

    @Column(name = "meter_start")
    private Integer meterStart;

    @Column(name = "meter_stop")
    private Integer meterStop;

    @Column(name = "unit")
    private String unit;

    @Column(name = "consumption")
    private BigDecimal consumption;

    @Column(name = "active_power")
    private BigDecimal activePower;

    @Column(name = "active_power_unit")
    private String activePowerUnit;

    @Column(name = "battery_percentage")
    private String batteryPercentage;

    @Column(name = "battery_percentage_start")
    private String batteryPercentageStart;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    @ColumnTransformer(write = "?::charging_session_reason")
    private ChargingSessionReasonType reason;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ChargingSession that = (ChargingSession) o;
        return reservation.equals(that.reservation);
    }

    @Override
    public int hashCode() {
        return reservation.hashCode();
    }
}
