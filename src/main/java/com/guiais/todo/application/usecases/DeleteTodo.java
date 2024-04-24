package com.guiais.todo.application.usecases;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.protocols.repositories.TodoRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteTodo {

  private final TodoRepository todoRepository;

  @Autowired
  public DeleteTodo(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Transactional
  public void execute(UUID id) {
    System.out.println("[DeleteTodo.execute] Deleting the todo: " + id);

    if (id == null) {
      throw new IllegalArgumentException("id is required");
    }

    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo == null) {
      throw new IllegalArgumentException("Todo not found");
    }

    todoRepository.deleteById(todo.getId());

    System.out.println(
      "[DeleteTodo.execute] Todo [" +
      todo.getId() +
      "] - " +
      todo.getTitle() +
      " deleted"
    );
  }
}
