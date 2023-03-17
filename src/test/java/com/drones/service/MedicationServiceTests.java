package com.drones.service;

import com.drones.domain.dto.MedicationDto;
import com.drones.domain.entity.MedicationEntity;
import com.drones.repository.MedicationRepository;
import com.drones.service.impl.MedicationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MedicationServiceTests {

  @InjectMocks
  private MedicationServiceImpl medicationService;
  @Mock
  private MedicationRepository repository;

  @BeforeEach
  public void setup() throws Exception{
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void getAllMedicationTest(){
    //Given
    Mockito.when(repository.findAll()).thenReturn(getMedications(2));
    int expectedSize = 2;
    //When
    List<MedicationDto> dtoList = medicationService.getAllMedication();
    int obtainedSize = dtoList.size();
    //Then
    Assertions.assertEquals(expectedSize, expectedSize);
  }

  @Test
  public void registerMedicationTest(){
    //Given
    long expectedId = 1l;
    Mockito.when(repository.save(Mockito.any())).thenReturn(getMedication(expectedId));
    //When
    MedicationDto result = medicationService.registerMedication(new MedicationDto());
    //Then
    Assertions.assertNotNull(result.getId());
    Assertions.assertEquals(expectedId, result.getId());

  }

  private Iterable<MedicationEntity> getMedications(int quantity){
    List<MedicationEntity> listMedications = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < quantity; i++){
      long id = random.nextLong() % 100l;
      if (id < 1) {
        id = -id; // Convertirlo en positivo
      }
      listMedications.add(getMedication(id));
    }
    return listMedications;
  }

  private MedicationEntity getMedication(Long id){
    MedicationEntity entity = new MedicationEntity();
    entity.setCode("AAAAABBBBBCCCC");
    entity.setId(id);
    entity.setName("PRODUCT_1");
    entity.setWeight(12.5);
    entity.setPathToImage("/image.jpg");
    return entity;
  }

}
