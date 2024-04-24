package com.guiais.todo.application.usecases;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.protocols.repositories.TodoRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompleteTodo {

  private final TodoRepository todoRepository;

  @Autowired
  public CompleteTodo(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Transactional
  public void execute(UUID id) {
    System.out.println("Completing the todo: " + id);

    if (id == null) {
      throw new IllegalArgumentException("id is required");
    }

    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo == null) {
      throw new IllegalArgumentException("Todo not found");
    }

    todo.moveToDone();

    todoRepository.save(todo);

    System.out.println(
      "[CompleteTodo.execute] Todo [" +
      todo.getId() +
      "] - " +
      todo.getTitle() +
      " moved to done"
    );
  }
}
