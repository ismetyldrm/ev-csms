package com.evcsms.chargestationserver.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateChargingSessionDTO {


    private Long version;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Integer reservationId;

    private String status;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private LocalDateTime unplugTime;

    private Integer meterStart;

    private Integer meterStop;

    private String unit;

    private BigDecimal consumption;

    private BigDecimal activePower;

    private String activePowerUnit;

    private String batteryPercentage;

    private String batteryPercentageStart;

    private String reason;

    @NotEmpty
    private List<@NotNull CreateChargingSessionDTO> chargingSessions;

}

