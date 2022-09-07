package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService service;
    private final CustomerService customerService;

    @Autowired
    public PetController(PetService service, CustomerService customerService) {
        this.service = service;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet entity = new Pet();
        entity.setType(petDTO.getType());
        entity.setName(petDTO.getName());
        if(petDTO.getBirthDate() != null){ entity.setBirthDate(petDTO.getBirthDate()); }
        if(petDTO.getOwnerId() > -1){
            Optional<Customer> customer = customerService.read(petDTO.getOwnerId());
            entity.setCustomer(customer.isPresent() ? customer.get() : null);
        }
        if(petDTO.getNotes() != null){ entity.setNotes(petDTO.getNotes()); }

        return map(service.create(entity));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<Pet> entity = service.read(petId);
        if(entity.isPresent()){
            return map(entity.get());
        }
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        Iterable<Pet> entities = service.readAll();
        return StreamSupport.stream(entities.spliterator(), false)
            .map(PetController::map)
            .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Optional<Customer> customer = customerService.read(ownerId);
        List<Pet> entities = new ArrayList<>();
        if(customer.isPresent()){
            entities = service.getPetsByCustomer(customer.get());
        }
        return entities.stream()
            .map(PetController::map)
            .collect(Collectors.toList());
    }

    private static PetDTO map(Pet pet){
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        if(pet.getType() != null) { petDTO.setType(pet.getType()); }
        if(pet.getName() != null) { petDTO.setName(pet.getName()); }
        if(pet.getCustomer() != null) { petDTO.setOwnerId(pet.getCustomer().getId()); }
        if(pet.getBirthDate() != null) { petDTO.setBirthDate(pet.getBirthDate()); }
        if(pet.getNotes() != null) { petDTO.setNotes(pet.getNotes()); }

        return petDTO;
    }
}
