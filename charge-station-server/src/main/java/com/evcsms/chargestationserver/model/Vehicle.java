package com.evcsms.chargestationserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "vehicle")
public class Vehicle extends TimestampedEntity {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "registration_plate")
    private String registrationPlate;

    @Column(name = "vehicle_identification_number")
    private String vehicleIdentificationNumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new LinkedHashSet<>();

}
