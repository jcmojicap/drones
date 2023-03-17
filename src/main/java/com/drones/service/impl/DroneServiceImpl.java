package com.drones.service.impl;

import com.drones.domain.dto.DeliverOrderRequestDto;
import com.drones.domain.dto.DeliverOrderResponseDto;
import com.drones.domain.dto.DroneDto;
import com.drones.domain.dto.MedicationDto;
import com.drones.domain.entity.DeliverEntity;
import com.drones.domain.entity.DroneEntity;
import com.drones.domain.entity.MedicationEntity;
import com.drones.domain.enums.DronStateEnum;
import com.drones.domain.mapper.DroneMapper;
import com.drones.domain.mapper.MedicationMapper;
import com.drones.repository.DeliveryRepository;
import com.drones.repository.DroneRepository;
import com.drones.service.DroneService;
import java.awt.SystemTray;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService {

  private final DroneRepository repository;
  private final DeliveryRepository deliveryRepository;

  public DroneServiceImpl(DroneRepository repository, DeliveryRepository deliveryRepository) {
    this.repository = repository;
    this.deliveryRepository = deliveryRepository;
  }

  @Override
  public List<DroneDto> getAvailableDrones() {
    List<DroneEntity> entityList = repository.findByState(DronStateEnum.IDLE.toString());
    if(!entityList.isEmpty()){
      return DroneMapper.INSTANCE.dronesEntitiesToDtos(entityList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public String checkBatteryLevel(Long droneId) {

    Optional<DroneEntity> entity = repository.findById(droneId);
    if(entity.isPresent()){
      return String.valueOf(entity.get().getBatteryCapacity());
    } else {
      return null;
    }
  }

  @Override
  public DroneDto registerDrone(DroneDto dto) {
    // clean the id in case the user filled it, since the id will be generated automatically
    dto.setId(null);
    DroneEntity entity = repository.save(DroneMapper.INSTANCE.droneDtoToEntity(dto));
    return DroneMapper.INSTANCE.droneEntityToDto(entity);
  }

  @Override
  public DeliverOrderResponseDto loadDrone(Long droneId, List<DeliverOrderRequestDto> medications) {
    DeliverOrderResponseDto response = new DeliverOrderResponseDto();
    Optional<DroneEntity> entity = repository.findById(droneId);
    if(entity.isPresent()){
      DroneEntity drone = entity.get();
      System.out.println("--------- drone.getState() " + drone.getState());
      if (!drone.getState().equals(DronStateEnum.IDLE.name())){
        response.setError("Drone's not available");
        return response;
      }
      System.out.println("--------- drone.getBatteryCapacity() " + drone.getBatteryCapacity());
      if (drone.getBatteryCapacity() < 15){
        response.setError("Drone's Battery too low");
        return response;
      }
      Double totalWeight = medications.stream().mapToDouble(
          f -> f.getQuantity() * f.getMedication().getWeight()
      ).sum();
      if (totalWeight > drone.getWeightLimit()){
        response.setError("Cargo too heavy for that Drone: Cargo weight -> " + totalWeight + " | Drone weight limit -> " + drone.getWeightLimit());
        return response;
      }

      String deliveryOrderCode = UUID.randomUUID().toString();
      for (DeliverOrderRequestDto f : medications){
        DeliverEntity deliverEntity = new DeliverEntity();
        deliverEntity.setDrone(drone);
        deliverEntity.setDeliverCode(deliveryOrderCode);
        deliverEntity.setMedication(MedicationMapper.INSTANCE.medicationDtoToEntity(f.getMedication()));
        deliverEntity.setQuantity(f.getQuantity());
        deliverEntity.setActive(true);
        System.out.println(" ------------------ delivery created ------------------");
        System.out.println(deliverEntity);
        try{
          deliveryRepository.save(deliverEntity);
        } catch (Exception e){
          System.out.println(" ------------------ Exception ------------------");
          System.out.println(" ------------------ Exception ------------------");
          System.out.println(e.getMessage());
          System.out.println(e.getCause());

        }
      }
      System.out.println(" ------------------ drone state ------------------");
      drone.setState(DronStateEnum.LOADING.name());
      System.out.println(" ------------------ drone persist ------------------");
      repository.save(drone);
      System.out.println(" ------------------ drone persisted ------------------");
      response.setTotalWeight(totalWeight);
      response.setDrone(drone.getModel());
    } else {
      response.setError("Drone not found");
    }
    return response;
  }
}
