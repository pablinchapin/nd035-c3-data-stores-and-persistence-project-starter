package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeService implements EntityService<Employee>{

  private final EmployeeRepository repository;

  @Autowired
  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public Employee create(Employee entity) {
    return repository.save(entity);
  }

  @Override
  public Optional<Employee> read(Long id) {
    return repository.findById(id);
  }

  @Override
  public Iterable<Employee> readAll() {
    return repository.findAll();
  }

  @Override
  public Employee update(Employee entity) {
    return repository.save(entity);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }


  public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
    Optional<Employee> employee = repository.findById(employeeId);
    if(employee.isPresent()){
      employee.get().setDaysSet(daysAvailable);
      repository.save(employee.get());
    }
  }

  public List<Employee> findEmployeesAvailableAndQualified(LocalDate date, Set<EmployeeSkill> skills) {
    return repository.getAllByDaysSet(date.getDayOfWeek())
        .stream()
        .filter(e -> e.getSkillSet().containsAll(skills))
        .collect(Collectors.toList());
  }
}
