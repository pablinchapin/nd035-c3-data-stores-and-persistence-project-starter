package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

  List<Schedule> getScheduleByPetListContains(Pet pet);
  List<Schedule> getSchedulesByEmployeeListContains(Employee employee);
  List<Schedule> getSchedulesByPetListIn(List<Pet> petList);

}
