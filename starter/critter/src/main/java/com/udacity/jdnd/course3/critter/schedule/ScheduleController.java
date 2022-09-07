package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final EmployeeService employeeService;
    private final PetService petService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, EmployeeService employeeService,
        PetService petService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        List<Employee> employeeList = new ArrayList<>();
        for(Long id : scheduleDTO.getEmployeeIds()){
            Optional<Employee> employee = employeeService.read(id);
            employee.ifPresent(employeeList::add);
        }

        List<Pet> petList = new ArrayList<>();
        for(Long id : scheduleDTO.getPetIds()){
            Optional<Pet> pet = petService.read(id);
            pet.ifPresent(petList::add);
        }

        schedule.setEmployeeList(employeeList);
        schedule.setPetList(petList);

        return mapScheduleDTO(scheduleService.create(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        Iterable<Schedule> schedules = scheduleService.readAll();
        return StreamSupport.stream(schedules.spliterator(), false)
            .map(ScheduleController::mapScheduleDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getSchedulesForPet(petId);
        return schedules.stream()
            .map(ScheduleController::mapScheduleDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesForEmployee(employeeId);
        return schedules.stream()
            .map(ScheduleController::mapScheduleDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getSchedulesForCustomer(customerId);
        return schedules.stream()
            .map(ScheduleController::mapScheduleDTO)
            .collect(Collectors.toList());
    }

    private static ScheduleDTO mapScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployeeList()
            .stream()
            .map(Employee::getId)
            .collect(Collectors.toList())
        );
        scheduleDTO.setPetIds(schedule.getPetList()
            .stream()
            .map(Pet::getId)
            .collect(Collectors.toList())
        );
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());

        return scheduleDTO;
    }
}
