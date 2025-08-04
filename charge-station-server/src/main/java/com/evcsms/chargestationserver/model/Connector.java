package com.evcsms.chargestationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "connector")
public class Connector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "index")
    private Integer index;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_type")
    private CurrentType currentType;

    @Column(name = "power_factor")
    private BigDecimal powerFactor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ConnectorStatusType status;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "charge_point_id")
    private ChargePoint chargePoint;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Connector connector = (Connector) o;
        return index.equals(connector.index);
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }
}
