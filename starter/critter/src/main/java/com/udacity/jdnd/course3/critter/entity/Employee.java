package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Nationalized
  private String name;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  private Set<EmployeeSkill> skillSet;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  private Set<DayOfWeek> daysSet;

  public Employee() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<EmployeeSkill> getSkillSet() {
    return skillSet;
  }

  public void setSkillSet(Set<EmployeeSkill> skillSet) {
    this.skillSet = skillSet;
  }

  public Set<DayOfWeek> getDaysSet() {
    return daysSet;
  }

  public void setDaysSet(Set<DayOfWeek> daysSet) {
    this.daysSet = daysSet;
  }
}
