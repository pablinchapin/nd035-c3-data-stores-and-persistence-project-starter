package com.udacity.jdnd.course3.critter.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Nationalized
  private String name;
  private String phone;
  @Nationalized
  private String notes;
  @OneToMany(targetEntity = Pet.class)//, mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL
  @ElementCollection
  private List<Pet> petList;

  public void addPet(Pet pet){
    petList.add(pet);
  }


  public Customer() {
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public List<Pet> getPetList() {
    return petList;
  }

  public void setPetList(List<Pet> petList) {
    this.petList = petList;
  }
}
