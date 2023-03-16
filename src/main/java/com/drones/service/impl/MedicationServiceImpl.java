package com.drones.service.impl;

import com.drones.domain.dto.MedicationDto;
import com.drones.domain.entity.MedicationEntity;
import com.drones.domain.mapper.MedicationMapper;
import com.drones.repository.MedicationRepository;
import com.drones.service.MedicationService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MedicationServiceImpl implements MedicationService {

  private final MedicationRepository repository;

  public MedicationServiceImpl(MedicationRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<MedicationDto> getAllMedication() {
    List<MedicationEntity> entityList = (List<MedicationEntity>) repository.findAll();
    if(!entityList.isEmpty()){
      return MedicationMapper.INSTANCE.medicationEntitiesToDtos(entityList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public MedicationDto registerMedication(MedicationDto dto) {
    // clean the id in case the user filled it, since the id will be generated automatically
    dto.setId(null);
    MedicationEntity entity = repository.save(MedicationMapper.INSTANCE.medicationDtoToEntity(dto));
    return MedicationMapper.INSTANCE.medicationEntityToDto(entity);
  }
}
