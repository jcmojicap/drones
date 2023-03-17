package com.drones.domain.dto;

import lombok.Data;

@Data
public class DeliverOrderRequestDto {
  private MedicationDto medication;
  private Integer quantity;

}
