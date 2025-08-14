package com.evcsms.chargestationserver.mapper;

import com.evcsms.chargestationserver.dto.CreateReservationDTO;
import com.evcsms.chargestationserver.model.Connector;
import com.evcsms.chargestationserver.model.Reservation;
import com.evcsms.chargestationserver.model.Vehicle;
import com.evcsms.chargestationserver.repository.ConnectorRepository;
import com.evcsms.chargestationserver.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {

    private VehicleRepository vehicleRepository;

    private ConnectorRepository connectorRepository;

    @Autowired
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Autowired
    public void setConnectorRepository(ConnectorRepository connectorRepository) {
        this.connectorRepository = connectorRepository;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "chargingSessions", ignore = true)
    @Mapping(target = "connector", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    public abstract Reservation toReservation(CreateReservationDTO dto);


    @AfterMapping
    protected void afterToReservation(@MappingTarget Reservation reservation, CreateReservationDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Vehicle not found with ID: " + dto.getVehicleId()));
        reservation.setVehicle(vehicle);

        Connector connector = connectorRepository.findById(dto.getConnectorId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Connector not found with ID: " + dto.getConnectorId()));
        reservation.setConnector(connector);
    }

    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "connectorId", source = "connector.id")
    public abstract CreateReservationDTO toCreateReservationDTO(Reservation reservation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "chargingSessions", ignore = true)
    @Mapping(target = "connector", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    public abstract Reservation toReservation(@MappingTarget Reservation reservation,CreateReservationDTO dto);

}
