package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PetService petService;

    public UserController(CustomerService customerService, EmployeeService employeeService,
        PetService petService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhoneNumber());

        if(customerDTO.getNotes() != null) { customer.setNotes(customerDTO.getNotes()); }

        if(customerDTO.getPetIds() != null ) {
            List<Pet> petList = customerDTO.getPetIds()
                .stream()
                .map(petService::read)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

            customer.setPetList(petList);
        }


        return map(customerService.create(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        Iterable<Customer> customers = customerService.readAll();
        return StreamSupport.stream(customers.spliterator(), false)
            .map(UserController::map)
            .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Optional<Customer> customer = customerService.getOwnerByPetId(petId);
        if(customer.isPresent()){
            return map(customer.get());
        }
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkillSet(employeeDTO.getSkills());
        employee.setDaysSet(employeeDTO.getDaysAvailable());

        return map(employeeService.create(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Optional<Employee> employee = employeeService.read(employeeId);
        if(employee.isPresent()){
            return map(employee.get());
        }
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.findEmployeesAvailableAndQualified(employeeDTO.getDate(), employeeDTO.getSkills());
        return employees.stream().map(UserController::map).collect(Collectors.toList());
    }

    private static CustomerDTO map(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        if(customer.getPhone() != null) { customerDTO.setPhoneNumber(customer.getPhone()); }
        if(customer.getNotes() != null) { customerDTO.setNotes(customer.getNotes()); }
        if(customer.getPetList() != null){
            customerDTO.setPetIds(customer.getPetList()
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList())
            );
        }

        return customerDTO;
    }
    
    private static EmployeeDTO map(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkillSet());
        employeeDTO.setDaysAvailable(employee.getDaysSet());
        return employeeDTO;
    }

}
