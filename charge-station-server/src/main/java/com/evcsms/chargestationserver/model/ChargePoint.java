package com.evcsms.chargestationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "charge_point")
public class ChargePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ocpp_id")
    private String ocppId;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "last_connected")
    private LocalDateTime lastConnected;

    @Column(name = "last_disconnected")
    private LocalDateTime lastDisconnected;

    @Column(name = "last_health_checked")
    private LocalDateTime lastHealthChecked;

    @ManyToOne
    @JoinColumn(name = "charge_station_id")
    private ChargeStation chargeStation;

    @OneToMany(mappedBy = "chargePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Connector> connectors = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ChargePoint that = (ChargePoint) o;
        return ocppId.equals(that.ocppId);
    }

    @Override
    public int hashCode() {
        return ocppId.hashCode();
    }

}// id, version, created, updated
