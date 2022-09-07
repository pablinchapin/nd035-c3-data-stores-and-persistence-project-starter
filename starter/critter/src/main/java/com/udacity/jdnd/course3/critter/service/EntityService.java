package com.udacity.jdnd.course3.critter.service;

import java.util.List;
import java.util.Optional;

public interface EntityService<T> {

  T           create(T entity);
  Optional<T> read(Long id);
  Iterable<T>     readAll();
  T           update(T entity);
  void        delete(Long id);

}
