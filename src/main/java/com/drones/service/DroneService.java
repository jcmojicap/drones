package com.drones.service;

import com.drones.domain.dto.DroneDto;
import com.drones.domain.entity.MedicationEntity;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface DroneService {

  ResponseEntity<List<DroneDto>> getAvailableDrones();
  ResponseEntity<Double> checkBatteryLevel(String serialNumber);
  ResponseEntity<DroneDto> registerDrone(DroneDto dto);
}
