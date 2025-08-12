package com.evcsms.chargestationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation extends TimestampedEntity {

    @Column(name = "connector_id")
    private Integer connectorId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatusType status;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private  Vehicle vehicle;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChargingSession> chargingSessions = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;
        return connectorId.equals(that.connectorId) && userId.equals(that.userId) && vehicle.equals(that.vehicle);
    }

    @Override
    public int hashCode() {
        int result = connectorId.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + vehicle.hashCode();
        return result;
    }
}
