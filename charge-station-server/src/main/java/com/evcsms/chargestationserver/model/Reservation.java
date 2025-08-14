package com.evcsms.chargestationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation extends TimestampedEntity {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ColumnTransformer(write = "?::reservation_status")
    private ReservationStatusType status = ReservationStatusType.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChargingSession> chargingSessions = new LinkedHashSet<>();

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "connector_id")
    private Connector connector;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;
        return userId.equals(that.userId) && beginTime.equals(that.beginTime) && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + beginTime.hashCode();
        result = 31 * result + endTime.hashCode();
        return result;
    }
}
