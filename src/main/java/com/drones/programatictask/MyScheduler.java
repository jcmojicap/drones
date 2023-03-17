package com.drones.programatictask;

import com.drones.domain.entity.DroneEntity;
import com.drones.repository.DroneRepository;
import java.util.Calendar;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class MyScheduler implements SchedulingConfigurer {

  private final DroneRepository repository;

  public MyScheduler(DroneRepository repository) {
    this.repository = repository;
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.addCronTask(new CronTask(() -> {
      validateBatteryDrones();
    }, "0 */5 * * * *")); // Cron para ejecutar la rutina cada 5 minutos
  }

  public void validateBatteryDrones(){
    Iterable<DroneEntity> drones = repository.findAll();
    System.out.println("Verifying drones battery level");
    for (DroneEntity drone: drones) {
      System.out.println("Date: " + Calendar.getInstance().getTime().toInstant() + " Model: " + drone.getModel() + " Battery level: " + drone.getBatteryCapacity());
      // TODO store these logs on a file
    }
  }
}