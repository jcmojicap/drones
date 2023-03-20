package com.drones.controller;

import com.drones.domain.dto.DeliverOrderRequestDto;
import com.drones.domain.dto.DeliverOrderResponseDto;
import com.drones.domain.dto.DroneDto;
import com.drones.domain.dto.DroneResponseDto;
import com.drones.domain.dto.MedicationDto;
import com.drones.domain.dto.MedicationResponseDto;
import com.drones.service.DroneService;
import com.drones.service.MedicationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DispatchController {

  private final DroneService droneService;
  private final MedicationService medicationService;

  private static final Integer MAX_CHARACTERS_SERIAL = 100;
  private static final String REGEXP_VALID_CHARACTERS_NAME = "^[a-zA-Z0-9_-]*$";
  private static final String REGEXP_VALID_CHARACTERS_CODE = "^[a-zA-Z0-9_]*$";


  public DispatchController(DroneService droneService, MedicationService medicationService) {
    this.droneService = droneService;
    this.medicationService = medicationService;
  }

  @ApiOperation(value = "Get the list of available drones")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = DroneDto.class, responseContainer = "List")
      })
  @GetMapping(value = "/drones/available", produces = "application/json; charset=UTF-8")
  public ResponseEntity<List<DroneDto>> getAvailableDrones(){
    return ResponseEntity.ok(droneService.getAvailableDrones());
  }

  @ApiOperation(value = "Get the battery Level of a drone",
      response = String.class
      )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "List"),
      @ApiResponse(code = 400, message= "{DroneId} is not valid Id")
  })
  @GetMapping(value = "/drones/drone/{droneId}/battery", produces = "application/json; charset=UTF-8")
  public ResponseEntity<String> getBatteryLevelDrone(@ApiParam(name = "droneId", type = "String", value="Drone's Id", required = true) @PathVariable(name = "droneId") String droneId){
    try{
      Long id = Long.valueOf(droneId);
      return new ResponseEntity<>("Battery Level: " + droneService.checkBatteryLevel(id)+" %", HttpStatus.OK);
    } catch (NumberFormatException ex){
      return new ResponseEntity<>("\"" + droneId + "\" Is not valid Id", HttpStatus.BAD_REQUEST);
    }

  }
  @ApiOperation(value = "Register a new drone",
      response = DroneDto.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = DroneDto.class, responseContainer = "List")
  })
  @PostMapping(value = "/drones/drone", produces = "application/json; charset=UTF-8")
  public ResponseEntity<DroneResponseDto> registerDrone( @RequestBody DroneDto dto){
    DroneResponseDto responseDto = new DroneResponseDto();
    if (dto.getSerialNumber().length() > MAX_CHARACTERS_SERIAL){
      dto.setSerialNumber(dto.getSerialNumber().substring(0, MAX_CHARACTERS_SERIAL));
      responseDto.setMessage("Serial number to big, it was truncated to 100 characters");
    }
    responseDto.setCode(HttpStatus.OK.toString());
    responseDto.setDrone(droneService.registerDrone(dto));
    return ResponseEntity.ok(responseDto);
  }

  @ApiOperation(value = "Load a drone with medications",
      response = DeliverOrderResponseDto.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = DeliverOrderResponseDto.class, responseContainer = "List"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @PostMapping(value=  "/drones/drone/{droneId}/load_medications", produces = "application/json; charset=UTF-8")
  public ResponseEntity<DeliverOrderResponseDto> loadDroneWithMedication(@PathVariable Long droneId, @RequestBody List<DeliverOrderRequestDto> deliverOrder){
    DeliverOrderResponseDto response = droneService.loadDrone(droneId, deliverOrder);
    if (response.getError() == null || response.getError().isEmpty()){
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.badRequest().body(response);
  }

  @ApiOperation(value = "Get the list of medications")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = MedicationDto.class, responseContainer = "List")
  })
  @GetMapping(value = "/medications/", produces = "application/json; charset=UTF-8")
  public ResponseEntity<List<MedicationDto>> getMedications(){
    return ResponseEntity.ok(medicationService.getAllMedication());
  }
  @ApiOperation(value = "Register new medications",
      response = DeliverOrderResponseDto.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = DeliverOrderResponseDto.class),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @PostMapping("/medications/medication")
  public ResponseEntity<MedicationResponseDto> registerMedication(@RequestBody MedicationDto dto){
    MedicationResponseDto responseDto = new MedicationResponseDto();
    StringBuilder responseMessage = new StringBuilder();
    boolean validRequest = true;
    if (!dto.getName().matches(REGEXP_VALID_CHARACTERS_NAME)){
      responseMessage.append("Name not valid, make sure the name only contains letters, numbers, - and _\n");
      validRequest = false;
    }
    if (!dto.getCode().matches(REGEXP_VALID_CHARACTERS_CODE)){
      responseMessage.append("Code not valid, make sure the name only contains letters, numbers and _");
      validRequest = false;
    } else {
      dto.setCode(dto.getCode().toUpperCase());
    }
    if (validRequest){
      responseDto.setCode(HttpStatus.OK.toString());
      responseDto.setMedication(medicationService.registerMedication(dto));
      return ResponseEntity.ok(responseDto);
    } else {
      responseDto.setCode(HttpStatus.BAD_REQUEST.toString());
      responseDto.setMessage(responseMessage.toString());
      return ResponseEntity.badRequest().body(responseDto);
    }
  }

}
