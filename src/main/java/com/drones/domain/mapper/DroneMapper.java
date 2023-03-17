package com.drones.domain.mapper;

import com.drones.domain.dto.DroneDto;
import com.drones.domain.entity.DroneEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DroneMapper {
  DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);

  DroneDto droneEntityToDto(DroneEntity entity);

  List<DroneDto> dronesEntitiesToDtos(List<DroneEntity> entities);
  @Mapping(target = "delivers", ignore = true)
  DroneEntity droneDtoToEntity(DroneDto dto);

  List<DroneEntity> droneDtosToEntities(List<DroneDto> dtos);
}
