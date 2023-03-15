package com.drones.domain.dto;

import com.drones.domain.enums.DronModelEnum;
import com.drones.domain.enums.DronStateEnum;
import lombok.Data;

@Data
public class DroneDto {
  private String serialNumber;
  private DronModelEnum model;
  private double weightLimit;
  private double batteryCapacity;
  private DronStateEnum state;

}
