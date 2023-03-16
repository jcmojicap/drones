package com.drones.domain.mapper;

import com.drones.domain.dto.MedicationDto;
import com.drones.domain.entity.MedicationEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicationMapper {

  MedicationMapper INSTANCE = Mappers.getMapper(MedicationMapper.class);

  MedicationDto medicationEntityToDto(MedicationEntity entity);

  List<MedicationDto> medicationEntitiesToDtos(List<MedicationEntity> entities);

  MedicationEntity medicationDtoToEntity(MedicationDto dto);
  List<MedicationEntity> medicationDtosToEntities(List<MedicationDto> dtos);

}
