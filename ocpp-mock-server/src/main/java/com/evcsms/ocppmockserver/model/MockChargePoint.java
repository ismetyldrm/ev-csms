package com.evcsms.ocppmockserver.model;

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
@Table(name = "mock_charge_point")
public class MockChargePoint extends TimestampedEntity {

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

    @OneToOne(mappedBy = "mockChargePoint", cascade = CascadeType.ALL, optional = false)
    private MockChargeHardwareSpec hardwareSpec;

    @OneToMany(mappedBy = "mockChargePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Connector> connectors = new LinkedHashSet<>();

    public void setHardwareSpec(MockChargeHardwareSpec hardwareSpec) {
        if (hardwareSpec != null) {
            hardwareSpec.setMockChargePoint(this);
            this.hardwareSpec = hardwareSpec;
        }
    }

    public void addConnector(Connector connector) {
        if (connector != null) {
            connector.setMockChargePoint(this);
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

        MockChargePoint that = (MockChargePoint) o;
        return ocppId.equals(that.ocppId);
    }

    @Override
    public int hashCode() {
        return ocppId.hashCode();
    }

}
