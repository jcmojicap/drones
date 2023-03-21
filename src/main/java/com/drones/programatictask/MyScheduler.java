package com.drones.programatictask;

import com.drones.domain.entity.DroneEntity;
import com.drones.repository.DroneRepository;
import java.util.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@Slf4j
public class MyScheduler implements SchedulingConfigurer {

  private final DroneRepository repository;

  public MyScheduler(DroneRepository repository) {
    this.repository = repository;
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.addCronTask(new CronTask(() -> {
      validateBatteryDrones();
    }, "*/5 * * * * *")); // Cron para ejecutar la rutina cada 5 minutos
  }

  public void validateBatteryDrones(){
    log.debug("----------------------- Validation of drone's battery started -------------------------");
    Iterable<DroneEntity> drones = repository.findAll();
    for (DroneEntity drone: drones) {
      log.debug("Date: " + Calendar.getInstance().getTime().toInstant() + " Model: " + drone.getModel() + " Battery level: " + drone.getBatteryCapacity());
    }
    log.debug("----------------------- Validation of drone's battery finished-------------------------");
  }
}