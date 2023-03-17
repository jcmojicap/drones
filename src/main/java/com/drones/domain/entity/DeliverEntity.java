package com.drones.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "DELIVER")
@ToString
public class DeliverEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "DELIVER_CODE")
  private String deliverCode;
  @ManyToOne
  @JoinColumn(name = "drone_id", referencedColumnName = "id")
  private DroneEntity drone;
  @ManyToOne
  @JoinColumn(name = "medication_id", referencedColumnName = "id")
  private MedicationEntity medication;

  private Integer quantity;
  private boolean active;

}
