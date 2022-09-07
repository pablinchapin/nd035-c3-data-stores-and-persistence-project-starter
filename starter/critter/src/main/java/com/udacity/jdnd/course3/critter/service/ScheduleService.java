package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ScheduleService implements EntityService<Schedule>{

  private final ScheduleRepository repository;
  private final PetRepository petRepository;
  private final EmployeeRepository employeeRepository;
  private final CustomerRepository customerRepository;

  @Autowired
  public ScheduleService(ScheduleRepository repository, PetRepository petRepository,
      EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
    this.repository = repository;
    this.petRepository = petRepository;
    this.employeeRepository = employeeRepository;
    this.customerRepository = customerRepository;
  }

  @Override
  public Schedule create(Schedule entity) {
    return repository.save(entity);
  }

  @Override
  public Optional<Schedule> read(Long id) {
    return repository.findById(id);
  }

  @Override
  public Iterable<Schedule> readAll() {
    return repository.findAll();
  }

  @Override
  public Schedule update(Schedule entity) {
    return repository.save(entity);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  public List<Schedule> getSchedulesForPet(Long petId){
    Optional<Pet> pet = petRepository.findById(petId);
    if(pet.isPresent()){
      return repository.getScheduleByPetListContains(pet.get());
    }
    throw new UnsupportedOperationException();
  }

  public List<Schedule> getSchedulesForEmployee(Long employeeId){
    Optional<Employee> employee = employeeRepository.findById(employeeId);
    if(employee.isPresent()){
      return repository.getSchedulesByEmployeeListContains(employee.get());
    }
    throw new UnsupportedOperationException();
  }

  public List<Schedule> getSchedulesForCustomer(Long customerId){
    Optional<Customer> customer = customerRepository.findById(customerId);
    if(customer.isPresent()){
      return repository.getSchedulesByPetListIn(customer.get().getPetList());
    }
    throw new UnsupportedOperationException();
  }

}
