package com.drones.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class MedicationResponseDto {
  private MedicationDto medication;
  private String message;
  private String code;
}
