package com.evcsms.chargestationserver.mapper;

import com.evcsms.chargestationserver.dto.*;
import com.evcsms.chargestationserver.model.ChargePoint;
import com.evcsms.chargestationserver.model.ChargeStation;
import com.evcsms.chargestationserver.model.Connector;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring"
)
public abstract class ChargeStationMapper {

    @Mapping(target = "id", ignore = true)
    public abstract ChargeStation toChargeStation(CreateChargeStationDTO dto);

    @AfterMapping
    public void setChargePoints(@MappingTarget ChargeStation chargeStation) {
        if (chargeStation.getChargePoints() != null) {
            for (ChargePoint chargePoint : chargeStation.getChargePoints()) {
                chargePoint.setChargeStation(chargeStation);
            }
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastConnected", ignore = true)
    @Mapping(target = "lastDisconnected", ignore = true)
    @Mapping(target = "lastHealthChecked", ignore = true)
    @Mapping(target = "online", ignore = true)
    @Mapping(target = "chargeStation", ignore = true)
    public abstract ChargePoint toChargePointForCreate(CreateChargePointDTO dto);

    //public abstract Set<ChargePoint> toCreateChargeStation(List<CreateChargePointDTO> dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chargePoints", ignore = true)
    public abstract void toChargeStation(@MappingTarget ChargeStation chargeStation, UpdateChargeStationDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastConnected", ignore = true)
    @Mapping(target = "lastDisconnected", ignore = true)
    @Mapping(target = "lastHealthChecked", ignore = true)
    @Mapping(target = "online", ignore = true)
    @Mapping(target = "chargeStation", ignore = true)
    public abstract void toChargePointForUpdate(@MappingTarget ChargePoint chargePoint, UpdateChargePointDTO dto);

    @AfterMapping
    public void setConnectors(@MappingTarget ChargePoint chargePoint) {
        if (chargePoint.getConnectors() != null) {
            for (Connector connector : chargePoint.getConnectors()) {
                connector.setChargePoint(chargePoint);
            }
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chargePoint", ignore = true)
    public abstract Connector toConnectorForCreate(CreateConnectorDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chargePoint", ignore = true)
    public abstract void toConnectorForUpdate(@MappingTarget Connector connector, UpdateConnectorDTO dto);

    public abstract List<CreateChargeStationDTO> toDTOList(List<ChargeStation> chargeStations);

    public abstract CreateChargeStationDTO toDTO(ChargeStation chargeStation);

}


