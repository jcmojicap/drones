package com.drones.domain.entity;

import com.drones.domain.enums.DronModelEnum;
import com.drones.domain.enums.DronStateEnum;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DRONE")
public class DroneEntity {

  @Id
  private String id;
  @Column(name = "SERIAL_NUMBER")
  private String serialNumber;
  @Column(name = "MODEL")
  private DronModelEnum model;
  @Column(name = "WEIGHT_LIMIT")
  private double weightLimit;
  @Column(name = "BATTERY_CAPACITY")
  private double batteryCapacity;
  @Column(name = "STATE")
  private DronStateEnum state;

}
