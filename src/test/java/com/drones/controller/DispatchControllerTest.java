package com.drones.controller;

import com.drones.domain.dto.DeliverOrderResponseDto;
import com.drones.domain.dto.DroneDto;
import com.drones.domain.enums.DronModelEnum;
import com.drones.domain.enums.DronStateEnum;
import com.drones.service.MedicationService;
import com.drones.service.impl.DroneServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DispatchControllerTest {

  @InjectMocks
  private DispatchController controller;

  @Mock
  private DroneServiceImpl droneService;

  @Mock
  MedicationService medicationService;

  @BeforeEach
  public void setup() throws Exception{
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void getAvailableDronesTest(){
    //Given
    int expectedSize = 2;
    Mockito.when(droneService.getAvailableDrones()).thenReturn(getAvailableDrones(expectedSize));
    //When
    List<DroneDto> obtained = controller.getAvailableDrones().getBody();
    //Then
    Assertions.assertEquals(expectedSize, obtained.size());
  }

  @Test
  public void getBatteryLevelDroneTest(){
    //Given
    String expected = "Battery Level: 50.0 %";
    Mockito.when(droneService.checkBatteryLevel(Mockito.anyLong())).thenReturn(getBatteryLevelDrone(getDrone()));
    //When
    String obtained = controller.getBatteryLevelDrone("12345").getBody();
    //Then
    Assertions.assertEquals(expected, obtained);
  }

  @Test
  public void getBatteryLevelDroneTest2(){
    //Given
    String id = "AAAAA";
    String expected = "\"" + id + "\" Is not valid Id";
    Mockito.when(droneService.checkBatteryLevel(Mockito.anyLong())).thenReturn(getBatteryLevelDrone(getDrone()));
    //When
    String obtained = controller.getBatteryLevelDrone(id).getBody();
    //Then
    Assertions.assertEquals(expected, obtained);
  }

  @Test
  public void loadDroneWithMedicationTest(){
    //Given
    double expectedWeight = 250.0;
    Mockito.when(droneService.loadDrone(Mockito.anyLong(), Mockito.any())).thenReturn(getDeliverOrderResponseDto(expectedWeight, null));
    //When
    DeliverOrderResponseDto obtained = controller.loadDroneWithMedication(12345l, new ArrayList<>()).getBody();
    //Then
    Assertions.assertEquals(expectedWeight, obtained.getTotalWeight());
    Assertions.assertNull(obtained.getError());
  }

  @Test
  public void loadDroneWithMedicationTest2(){
    //Given
    double expectedWeight = 250.0;
    String expectedError = "Drone not found";
    Mockito.when(droneService.loadDrone(Mockito.anyLong(), Mockito.any())).thenReturn(getDeliverOrderResponseDto(expectedWeight, expectedError));
    //When
    DeliverOrderResponseDto obtained = controller.loadDroneWithMedication(12345l, new ArrayList<>()).getBody();
    //Then
    Assertions.assertEquals(expectedError, obtained.getError());
  }

  @Test
  public void loadDroneWithMedicationTest3(){
    //Given
    double expectedWeight = 250.0;
    String expectedError = "Drone's not available";
    Mockito.when(droneService.loadDrone(Mockito.anyLong(), Mockito.any())).thenReturn(getDeliverOrderResponseDto(expectedWeight, expectedError));
    //When
    DeliverOrderResponseDto obtained = controller.loadDroneWithMedication(12345l, new ArrayList<>()).getBody();
    //Then
    Assertions.assertEquals(expectedError, obtained.getError());
  }

  @Test
  public void loadDroneWithMedicationTest4(){
    //Given
    double expectedWeight = 250.0;
    String expectedError = "Drone's Battery too low";
    Mockito.when(droneService.loadDrone(Mockito.anyLong(), Mockito.any())).thenReturn(getDeliverOrderResponseDto(expectedWeight, expectedError));
    //When
    DeliverOrderResponseDto obtained = controller.loadDroneWithMedication(12345l, new ArrayList<>()).getBody();
    //Then
    Assertions.assertEquals(expectedError, obtained.getError());
  }

  @Test
  public void loadDroneWithMedicationTest5(){
    //Given
    double expectedWeight = 250.0;
    String expectedError = "Cargo too heavy for that Drone: Cargo weight -> " + expectedWeight + " | Drone weight limit -> " + expectedWeight;
    Mockito.when(droneService.loadDrone(Mockito.anyLong(), Mockito.any())).thenReturn(getDeliverOrderResponseDto(expectedWeight, expectedError));
    //When
    DeliverOrderResponseDto obtained = controller.loadDroneWithMedication(12345l, new ArrayList<>()).getBody();
    //Then
    Assertions.assertEquals(expectedError, obtained.getError());
  }

  private DeliverOrderResponseDto getDeliverOrderResponseDto(double weight, String errorMessage){
    DeliverOrderResponseDto responseDto = new DeliverOrderResponseDto();
    responseDto.setDrone("lightweight");
    responseDto.setTotalWeight(weight);
    responseDto.setError(errorMessage);
    return responseDto;
  }
  private List<DroneDto> getAvailableDrones(int expectedSize){
    List<DroneDto> list = new ArrayList<>();
    for (int i = 0; i<expectedSize; i++){
      list.add(getDrone());
    }
    return list;
  }

  private String getBatteryLevelDrone(DroneDto dto){
    return String.valueOf(dto.getBatteryCapacity());
  }

  private DroneDto getDrone(){
    DroneDto dto = new DroneDto();
    dto.setModel(DronModelEnum.Cruiserweight);
    dto.setState(DronStateEnum.IDLE);
    dto.setSerialNumber("AAA000000A");
    dto.setBatteryCapacity(50);
    dto.setWeightLimit(100);
    return dto;
  }

}
