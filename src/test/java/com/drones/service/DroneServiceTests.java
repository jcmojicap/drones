package com.drones.service;

import com.drones.domain.dto.DeliverOrderRequestDto;
import com.drones.domain.dto.DeliverOrderResponseDto;
import com.drones.domain.dto.DroneDto;
import com.drones.domain.dto.MedicationDto;
import com.drones.domain.entity.DroneEntity;
import com.drones.domain.entity.MedicationEntity;
import com.drones.repository.DeliveryRepository;
import com.drones.repository.DroneRepository;
import com.drones.service.impl.DroneServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DroneServiceTests {

  @InjectMocks
  private DroneServiceImpl droneService;
  @Mock
  private DroneRepository repository;
  @Mock
  private DeliveryRepository deliveryRepository;

  @BeforeEach
  public void setup() throws Exception{
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void getAvailableDronesTest(){
    //Given
    int expectedSize = 2;
    Mockito.when(repository.findByState(Mockito.anyString())).thenReturn(getDrones(expectedSize));
    //When
    List<DroneDto> obtained = droneService.getAvailableDrones();
    //Then
    Assertions.assertEquals(expectedSize, obtained.size());
  }

  @Test
  public void checkBatteryLevelTest(){
    //Given
    Long id = 1l;
    String expected = "50.0";
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(getDrone(id)));
    //When
    String obtained = droneService.checkBatteryLevel(id);
    //Then
    Assertions.assertEquals(expected, obtained);
  }

  @Test
  public void checkBatteryLevelTest2(){
    //Given
    String expected = null;
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    //When
    String obtained = droneService.checkBatteryLevel(1l);
    //Then
    Assertions.assertEquals(expected, obtained);
  }

  @Test
  public void registerDroneTest(){
    //Given
    long expectedId = 1l;
    Mockito.when(repository.save(Mockito.any())).thenReturn(getDrone(expectedId));
    //When
    DroneDto result = droneService.registerDrone(new DroneDto());
    //Then
    Assertions.assertNotNull(result.getId());
    Assertions.assertEquals(expectedId, result.getId());
  }

  @Test
  public void loadDroneTest(){
    //Given
    String expected = "Drone not found";
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    //When
    DeliverOrderResponseDto obtained = droneService.loadDrone(1l, new ArrayList<>());
    //Then
    Assertions.assertEquals(expected, obtained.getError());
  }

  @Test
  public void loadDroneTest2(){
    //Given
    String expected = "Drone's not available";
    long id = 1l;
    String state = "DELIVERED";
    double batteryLevel = 50.0;
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(getDrone(id, state, batteryLevel)));
    //When
    DeliverOrderResponseDto obtained = droneService.loadDrone(1l, new ArrayList<>());
    //Then
    Assertions.assertEquals(expected, obtained.getError());
  }

  @Test
  public void loadDroneTest3(){
    //Given
    String expected = "Drone's Battery too low";
    long id = 1l;
    String state = "IDLE";
    double batteryLevel = 10.0;
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(getDrone(id, state, batteryLevel)));
    //When
    DeliverOrderResponseDto obtained = droneService.loadDrone(1l, new ArrayList<>());
    //Then
    Assertions.assertEquals(expected, obtained.getError());
  }

  @Test
  public void loadDroneTest4(){
    //Given
    String expected = "Cargo too heavy for that Drone: Cargo weight -> 500.0 | Drone weight limit -> 100.0";
    long id = 1l;
    String state = "IDLE";
    double batteryLevel = 80.0;
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(getDrone(id, state, batteryLevel)));
    //When
    DeliverOrderResponseDto obtained = droneService.loadDrone(1l, getDeliverList());
    //Then
    Assertions.assertEquals(expected, obtained.getError());
  }

  @Test
  public void loadDroneTest5(){
    //Given
    Double expected = 500.0;
    long id = 1l;
    String state = "IDLE";
    double batteryLevel = 80.0;
    double maxWeight = 10000;
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(getDrone(id, state, batteryLevel, maxWeight)));
    //When
    DeliverOrderResponseDto obtained = droneService.loadDrone(1l, getDeliverList());
    //Then
    Assertions.assertEquals(expected, obtained.getTotalWeight());
  }


  private List<DroneEntity> getDrones(int quantity){
    List<DroneEntity> list = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < quantity; i++){
      long id = random.nextLong() % 100l;
      if (id < 1) {
        id = -id; // Convertirlo en positivo
      }
      list.add(getDrone(id));
    }
    return list;
  }

  private DroneEntity getDrone(Long id){
    DroneEntity entity = new DroneEntity();
    entity.setId(id);
    entity.setModel("Cruiserweight");
    entity.setState("IDLE");
    entity.setSerialNumber("AAA000000A");
    entity.setBatteryCapacity(50);
    entity.setWeightLimit(100);
    return entity;
  }

  private DroneEntity getDrone(Long id, String state, Double batteryLevel){
    DroneEntity entity = getDrone(id);
    entity.setState(state);
    entity.setBatteryCapacity(batteryLevel);
    return entity;
  }

  private DroneEntity getDrone(Long id, String state, Double batteryLevel, Double maxWeight){
    DroneEntity entity = getDrone(id, state, batteryLevel);
    entity.setWeightLimit(maxWeight);
    return entity;
  }

  private List<DeliverOrderRequestDto> getDeliverList(){
    List<DeliverOrderRequestDto> list = new ArrayList<>();
    list.add(getDeliverOrder());
    return list;
  }

  private DeliverOrderRequestDto getDeliverOrder(){
    DeliverOrderRequestDto dto = new DeliverOrderRequestDto();
    dto.setQuantity(5);
    dto.setMedication(getMedication(1l));
    return dto;
  }

  private MedicationDto getMedication(Long id){
    MedicationDto dto = new MedicationDto();
    dto.setCode("AAAAABBBBBCCCC");
    dto.setName("PRODUCT_1");
    dto.setWeight(100.0);
    dto.setPathToImage("/image.jpg");
    return dto;
  }

}
