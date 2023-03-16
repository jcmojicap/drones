package com.drones.service;

import com.drones.domain.dto.MedicationDto;
import com.drones.domain.entity.MedicationEntity;
import java.util.List;

public interface MedicationService {
  List<MedicationDto> getAllMedication();

  MedicationDto registerMedication(MedicationDto dto);

}
