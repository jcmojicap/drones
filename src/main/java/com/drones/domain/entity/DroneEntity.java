package com.drones.domain.entity;

import com.drones.domain.enums.DronModelEnum;
import com.drones.domain.enums.DronStateEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DRONE")
public class DroneEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "SERIAL_NUMBER")
  private String serialNumber;
  @Column(name = "MODEL")
  private String model;
  @Column(name = "WEIGHT_LIMIT")
  private double weightLimit;
  @Column(name = "BATTERY_CAPACITY")
  private double batteryCapacity;
  @Column(name = "STATE")
  private String state;

}
