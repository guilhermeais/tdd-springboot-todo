package com.guiais.todo.application.usecases;

import com.guiais.todo.application.usecases.dtos.CreateTodoDto;
import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.protocols.repositories.TodoRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTodo {

  private final TodoRepository todoRepository;

  @Autowired
  public CreateTodo(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Transactional
  public Todo execute(CreateTodoDto dto) {
    String title = dto.getTitle();
    String description = dto.getDescription();

    System.out.println(
      "Creating a new todo with title: " +
      title +
      " and description: " +
      description
    );

    if (title == null || title.isEmpty()) {
      throw new IllegalArgumentException("Title is required");
    }

    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description is required");
    }

    Todo todo = Todo.builder().title(title).description(description).build();
    todoRepository.save(todo);

    System.out.println(
      "[CreateTodo.execute] Todo [" +
      todo.getId() +
      "] - " +
      todo.getTitle() +
      " created"
    );

    return todo;
  }
}
