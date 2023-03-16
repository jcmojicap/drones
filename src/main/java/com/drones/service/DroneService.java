package com.drones.service;

import com.drones.domain.dto.DroneDto;
import com.drones.domain.entity.MedicationEntity;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface DroneService {

  List<DroneDto> getAvailableDrones();
  String checkBatteryLevel(Long droneId);
  DroneDto registerDrone(DroneDto dto);
}
