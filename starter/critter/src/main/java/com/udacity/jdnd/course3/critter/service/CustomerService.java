package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService implements EntityService<Customer> {

  private final CustomerRepository repository;
  private final PetRepository petRepository;

  @Autowired
  public CustomerService(CustomerRepository repository, PetRepository petRepository) {
    this.repository = repository;
    this.petRepository = petRepository;
  }

  @Override
  public Customer create(Customer entity) {
    return repository.save(entity);
  }

  @Override
  public Optional<Customer> read(Long id) {
    return repository.findById(id);
  }

  @Override
  public Iterable<Customer> readAll() {
    return repository.findAll();
  }

  @Override
  public Customer update(Customer entity) {
    return repository.save(entity);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  public Optional<Customer> getOwnerByPetId(Long petId){
    Optional<Pet> pet = petRepository.findById(petId);

    return Optional.ofNullable(pet.get().getCustomer());
  }

}
