package com.guiais.todo.mocks.applications.protocols.repositories;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.protocols.repositories.TodoRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTodoRepository implements TodoRepository {

  private HashMap<UUID, Todo> todos = new HashMap<>();

  @Override
  public Todo save(Todo todo) {
    todos.put(todo.getId(), todo);
    return todo;
  }

  @Override
  public Optional<Todo> findById(UUID id) {
    Todo todo = todos.get(id);
    return Optional.ofNullable(todo);
  }

  @Override
  public List<Todo> findAll() {
    return List.copyOf(todos.values());
  }

  @Override
  public void deleteById(UUID id) {
    todos.remove(id);
  }

  public void clear() {
    todos.clear();
  }
}
