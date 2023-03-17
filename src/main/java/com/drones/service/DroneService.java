package com.drones.service;

import com.drones.domain.dto.DeliverOrderRequestDto;
import com.drones.domain.dto.DeliverOrderResponseDto;
import com.drones.domain.dto.DroneDto;
import com.drones.domain.entity.MedicationEntity;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface DroneService {

  List<DroneDto> getAvailableDrones();
  String checkBatteryLevel(Long droneId);
  DroneDto registerDrone(DroneDto dto);

  DeliverOrderResponseDto loadDrone(Long droneId, List<DeliverOrderRequestDto> medications);
}
