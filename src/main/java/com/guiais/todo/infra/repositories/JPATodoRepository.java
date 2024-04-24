package com.guiais.todo.infra.repositories;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.protocols.repositories.TodoRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPATodoRepository
  extends TodoRepository, JpaRepository<Todo, UUID> {}
