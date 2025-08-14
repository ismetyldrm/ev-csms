package com.evcsms.chargestationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "charge_point")
public class ChargePoint extends TimestampedEntity {

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

    public void addConnector(Connector connector) {
        if (connector != null) {
            connector.setChargePoint(this);
            connectors.add(connector);
        }
    }

    public Optional<Connector> findConnector(Integer index) {
        return connectors.stream()
                .filter(connector -> connector.getIndex().equals(index))
                .findFirst();
    }

    public void removeIfNotIn(List<Integer> indexList) {
        this.connectors.removeIf(cp -> !indexList.contains(cp.getIndex()));
    }

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
