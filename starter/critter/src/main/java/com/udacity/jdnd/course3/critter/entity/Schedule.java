package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate date;
  @ElementCollection
  private Set<EmployeeSkill> activities;
  @ManyToMany()//(targetEntity = Employee.class)
  @JoinTable(
      name = "employee_schedules",
      joinColumns = {@JoinColumn(name = "schedule_id")},
      inverseJoinColumns = {@JoinColumn(name = "employee_id")}
  )
  private List<Employee> employeeList;
  @ManyToMany()//(targetEntity = Pet.class)
  @JoinTable(
      name = "pet_schedules",
      joinColumns = {@JoinColumn(name = "schedule_id")},
      inverseJoinColumns = {@JoinColumn(name = "pet_id")}
  )
  private List<Pet> petList;

  public Schedule() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Set<EmployeeSkill> getActivities() {
    return activities;
  }

  public void setActivities(Set<EmployeeSkill> activities) {
    this.activities = activities;
  }

  public List<Employee> getEmployeeList() {
    return employeeList;
  }

  public void setEmployeeList(
      List<Employee> employeeList) {
    this.employeeList = employeeList;
  }

  public List<Pet> getPetList() {
    return petList;
  }

  public void setPetList(List<Pet> petList) {
    this.petList = petList;
  }
}
