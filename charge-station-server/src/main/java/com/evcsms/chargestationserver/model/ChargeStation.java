package com.evcsms.chargestationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "charge_station")
public class ChargeStation extends TimestampedEntity {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @ColumnTransformer(write = "?::charge_station_type")
    private ChargeStationType type;

    @Column(name = "parking_slots")
    private Integer parkingSlots;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @OneToMany(mappedBy = "chargeStation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChargePoint> chargePoints = new LinkedHashSet<>();

    public void addChargePoint(ChargePoint chargePoint) {
        if (chargePoint != null) {
            chargePoints.add(chargePoint);
            chargePoint.setChargeStation(this);
        }
    }

    public Optional<ChargePoint> findChargePoint(String ocppId) {
        for (ChargePoint chargePoint : chargePoints) {
            if (chargePoint.getOcppId().equals(ocppId)) {
                return Optional.of(chargePoint);
            }
        }
        return Optional.empty();
    }

    public void removeIfNotIn(List<String> ocppIdList) {
        this.chargePoints.removeIf(cp -> !ocppIdList.contains(cp.getOcppId()));
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ChargeStation that = (ChargeStation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
