package com.evcsms.ocppmockserver.mapper;

import com.evcsms.ocppmockserver.dto.*;
import com.evcsms.ocppmockserver.model.MockChargePoint;
import com.evcsms.ocppmockserver.model.Connector;
import com.evcsms.ocppmockserver.model.MockChargeHardwareSpec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ChargePointMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "lastConnected", ignore = true)
    @Mapping(target = "lastDisconnected", ignore = true)
    @Mapping(target = "lastHealthChecked", ignore = true)
    @Mapping(target = "online", ignore = true)
    @Mapping(target = "connectors", ignore = true)
    @Mapping(target = "hardwareSpec", ignore = true)
    public abstract MockChargePoint toChargePointForCreate(CreateChargePointDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "mockChargePoint", ignore = true)
    public abstract MockChargeHardwareSpec toMockChargeHardwareSpec(CreateChargeHardwareSpecDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "mockChargePoint", ignore = true)
    public abstract Connector toConnectorForCreate(CreateConnectorDTO dto);

    public abstract CreateChargePointDTO toDTO(MockChargePoint mockChargePoint);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "lastConnected", ignore = true)
    @Mapping(target = "lastDisconnected", ignore = true)
    @Mapping(target = "lastHealthChecked", ignore = true)
    @Mapping(target = "online", ignore = true)
    @Mapping(target = "connectors", ignore = true)
    @Mapping(target = "hardwareSpec", ignore = true)
    public abstract void toChargePointForUpdate(@MappingTarget MockChargePoint mockChargePoint, UpdateChargePointDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "mockChargePoint", ignore = true)
    public abstract void toConnectorForUpdate(@MappingTarget Connector connector, UpdateConnectorDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "mockChargePoint", ignore = true)
    public abstract void toMockChargeHardwareSpecForUpdate(@MappingTarget MockChargeHardwareSpec mockChargeHardwareSpec, UpdateChargeHardwareSpecDTO dto);

}
