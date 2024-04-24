package com.guiais.todo.application.usecases;

import com.guiais.todo.application.usecases.dtos.UpdateTodoDto;
import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.protocols.repositories.TodoRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTodo {

  private final TodoRepository todoRepository;

  @Autowired
  public UpdateTodo(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Transactional
  public void execute(UUID id, UpdateTodoDto request) {
    String title = request.getTitle();
    String description = request.getDescription();

    System.out.println(
      "Updating todo " +
      id.toString() +
      " with title: " +
      title +
      " and description: " +
      description
    );

    Todo todo = todoRepository.findById(id).orElse(null);

    if (todo == null) {
      throw new IllegalArgumentException("Todo not found");
    }

    if (title != null) {
      todo.setTitle(title);
    }

    if (description != null) {
      todo.setDescription(description);
    }

    todoRepository.save(todo);

    System.out.println(
      "[UpdateTodo.execute] Todo [" +
      todo.getId() +
      "] - " +
      todo.getTitle() +
      " updated"
    );
  }
}
