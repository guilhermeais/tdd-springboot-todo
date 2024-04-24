package com.guiais.todo.infra.repositories;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.protocols.repositories.TodoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPATodoRepository
  extends TodoRepository, JpaRepository<Todo, UUID> {
  Todo save(Todo todo);
  Optional<Todo> findById(UUID id);
  List<Todo> findAll();
  void deleteById(UUID id);
}
