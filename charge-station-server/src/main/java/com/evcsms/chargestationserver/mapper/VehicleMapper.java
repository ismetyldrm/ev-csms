package com.evcsms.chargestationserver.mapper;

import com.evcsms.chargestationserver.dto.CreateChargeStationDTO;
import com.evcsms.chargestationserver.dto.CreateVehicleDTO;
import com.evcsms.chargestationserver.model.ChargeStation;
import com.evcsms.chargestationserver.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
       componentModel = "spring"
)
public abstract class VehicleMapper {


    @Mapping(target = "id",ignore = true)
    @Mapping(target = "version",ignore = true)
    @Mapping(target = "created",ignore = true)
    @Mapping(target = "updated",ignore = true)
    public abstract Vehicle toVehicle(CreateVehicleDTO dto);


    @Mapping(target = "version",ignore = true)
    @Mapping(target = "created",ignore = true)
    @Mapping(target = "updated",ignore = true)
    public abstract CreateVehicleDTO toCreateVehicleDTO(Vehicle vehicle);




}
