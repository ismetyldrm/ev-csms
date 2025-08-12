package com.evcsms.chargestationserver.service.Impl;


import com.evcsms.chargestationserver.dto.CreateVehicleDTO;
import com.evcsms.chargestationserver.mapper.VehicleMapper;
import com.evcsms.chargestationserver.model.Vehicle;
import com.evcsms.chargestationserver.repository.VehicleRepository;
import com.evcsms.chargestationserver.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public Long createVehicle(CreateVehicleDTO dto) {
        Vehicle vehicle = vehicleMapper.toVehicle(dto);
        Vehicle newVehicle = vehicleRepository.save(vehicle);
        return newVehicle.getId();
    }

    public Page<CreateVehicleDTO> getAllVehicles(Pageable pageable){
        return vehicleRepository.findAll(pageable)
                .map(vehicleMapper::toCreateVehicleDTO);
    }

    public CreateVehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with id " + id + " does not exist"));
        return vehicleMapper.toCreateVehicleDTO(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle with id " + id + " does not exist"));
        vehicleRepository.delete(vehicle);
    }



}
