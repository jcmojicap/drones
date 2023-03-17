package com.drones.domain.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "MEDICATION")
public class MedicationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Double weight;
  private String code;
  @Column(name = "IMAGE")
  private String pathToImage;
  @OneToMany(mappedBy = "medication")
  private List<DeliverEntity> delivers;

}
