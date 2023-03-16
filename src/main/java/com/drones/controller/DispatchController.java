package com.drones.controller;

import com.drones.domain.dto.DroneDto;
import com.drones.service.DroneService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DispatchController {

  private final DroneService droneService;

  public DispatchController(DroneService droneService) {
    this.droneService = droneService;
  }


  @GetMapping("/drones/available")
  public ResponseEntity<List<DroneDto>> getAvailableDrones(){
    return droneService.getAvailableDrones();
  }

  @GetMapping("/drones/drone/{serialNumber}/battery")
  public ResponseEntity<Double> getBatteryLevelDrone(@PathVariable(name = "serialNumber") String serialNumber){
    return droneService.checkBatteryLevel(serialNumber);
  }

  @PostMapping("/drones/drone")
  public ResponseEntity<DroneDto> registerDrone(@RequestBody DroneDto dto){
    return droneService.registerDrone(dto);
  }
}
