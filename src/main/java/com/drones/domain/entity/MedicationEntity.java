package com.drones.domain.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "MEDICATION")
public class MedicationEntity {

  @Id
  private String id;
  private String name;
  private Double weight;
  private String code;
  @Column(name = "IMAGE")
  private String pathToImage;

}
