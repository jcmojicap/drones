package com.drones.domain.enums;

import java.util.Arrays;

public enum DronModelEnum {
  Lightweight, Middleweight, Cruiserweight, Heavyweight;

  public static DronModelEnum findByName(String name){
    for (DronModelEnum dronModelEnum : values()){
      if (dronModelEnum.name().equalsIgnoreCase(name)){
        return dronModelEnum;
      }
    }
    return null;
  }
}
