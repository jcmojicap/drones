package com.drones.service.impl;

import com.drones.domain.dto.DroneDto;
import com.drones.domain.entity.DroneEntity;
import com.drones.domain.enums.DronStateEnum;
import com.drones.domain.mapper.DroneMapper;
import com.drones.repository.DroneRepository;
import com.drones.service.DroneService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService {

  private final DroneRepository repository;

  public DroneServiceImpl(DroneRepository repository) {
    this.repository = repository;
  }
  @Override
  public List<DroneDto> getAvailableDrones() {
    List<DroneEntity> entityList = repository.findByState(DronStateEnum.IDLE.toString());
    if(!entityList.isEmpty()){
      return DroneMapper.INSTANCE.dronesEntitiesToDtos(entityList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public String checkBatteryLevel(Long droneId) {

    Optional<DroneEntity> entity = repository.findById(droneId);
    if(entity.isPresent()){
      return String.valueOf(entity.get().getBatteryCapacity());
    } else {
      return null;
    }
  }

  @Override
  public DroneDto registerDrone(DroneDto dto) {
    // clean the id in case the user filled it, since the id will be generated automatically
    dto.setId(null);
    DroneEntity entity = repository.save(DroneMapper.INSTANCE.droneDtoToEntity(dto));
    return DroneMapper.INSTANCE.droneEntityToDto(entity);
  }
}
