package com.guiais.todo.domain.protocols.repositories;

import com.guiais.todo.domain.entities.Todo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository {
  Todo save(Todo todo);
  Optional<Todo> findById(UUID id);
  List<Todo> findAll();
  void deleteById(UUID id);
}
