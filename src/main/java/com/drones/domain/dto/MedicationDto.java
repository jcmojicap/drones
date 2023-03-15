package com.drones.domain.dto;

import lombok.Data;


@Data
public class MedicationDto {
  private String name;
  private Double weight;
  private String code;
  private String pathToImage;
}
