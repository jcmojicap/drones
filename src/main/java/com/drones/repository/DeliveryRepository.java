package com.drones.repository;

import com.drones.domain.entity.DeliverEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends CrudRepository<DeliverEntity, Long> {

}
