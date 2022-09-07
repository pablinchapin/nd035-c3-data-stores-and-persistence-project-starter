package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PetService implements EntityService<Pet>{

  private final PetRepository repository;
  private final CustomerRepository customerRepository;

  @Autowired
  public PetService(PetRepository repository, CustomerRepository customerRepository) {
    this.repository = repository;
    this.customerRepository = customerRepository;
  }

  @Override
  public Pet create(Pet entity) {
    entity = repository.save(entity);
    entity.getCustomer().addPet(entity);
    customerRepository.save(entity.getCustomer());
    //return repository.save(entity);
    return entity;
  }

  @Override
  public Optional<Pet> read(Long id) {
    return repository.findById(id);
  }

  @Override
  public Iterable<Pet> readAll() {
    return repository.findAll();
  }

  @Override
  public Pet update(Pet entity) {
    return repository.save(entity);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  public List<Pet> getPetsByCustomer(Customer entity){
    return repository.getPetsByCustomer(entity);
  }

}
