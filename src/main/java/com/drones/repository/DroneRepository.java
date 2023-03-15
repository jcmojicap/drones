package com.drones.repository;

import com.drones.domain.entity.DroneEntity;
import com.drones.domain.enums.DronStateEnum;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends CrudRepository<DroneEntity, String> {
  List<DroneEntity> findByState(String status);

}
