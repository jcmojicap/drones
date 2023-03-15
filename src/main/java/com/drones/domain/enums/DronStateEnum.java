package com.drones.domain.enums;

public enum DronStateEnum {
  IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING;

  public static DronStateEnum findByName(String name){
    for (DronStateEnum dronModelEnum : values()){
      if (dronModelEnum.name().equalsIgnoreCase(name)){
        return dronModelEnum;
      }
    }
    return null;
  }
}
