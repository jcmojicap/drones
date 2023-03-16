package com.drones.repository;

import com.drones.domain.entity.DroneEntity;
import com.drones.domain.entity.MedicationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends CrudRepository<MedicationEntity, Long> {

}
