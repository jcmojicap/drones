package com.drones.service.impl;

import com.drones.domain.dto.DroneDto;
import com.drones.domain.entity.DroneEntity;
import com.drones.domain.entity.MedicationEntity;
import com.drones.domain.enums.DronStateEnum;
import com.drones.domain.mapper.DroneMapper;
import com.drones.repository.DroneRepository;
import com.drones.service.DroneService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService {

  private final DroneRepository droneRepository;

  public DroneServiceImpl(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }
  @Override
  public ResponseEntity<List<DroneDto>> getAvailableDrones() {
    List<DroneEntity> entityList = droneRepository.findByState(DronStateEnum.IDLE.toString());
    if(!entityList.isEmpty()){
      return ResponseEntity.ok(DroneMapper.INSTANCE.dronesEntitiesToDtos(entityList));
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @Override
  public ResponseEntity<Double> checkBatteryLevel(String serialNumber) {

    Optional<DroneEntity> entity = Optional.ofNullable(droneRepository.findBySerialNumber(serialNumber));
    if(entity.isPresent()){
      return ResponseEntity.ok(entity.get().getBatteryCapacity());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<DroneDto> registerDrone(DroneDto dto) {
    DroneEntity entity = droneRepository.save(DroneMapper.INSTANCE.droneDtoToEntity(dto));
    return ResponseEntity.ok(DroneMapper.INSTANCE.droneEntityToDto(entity));
  }
}
