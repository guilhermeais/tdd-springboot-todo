package com.guiais.todo.application.usecases;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.protocols.repositories.TodoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListTodos {

  private final TodoRepository todoRepository;

  @Autowired
  public ListTodos(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Transactional(readOnly = true)
  public List<Todo> execute() {
    System.out.println("[ListTodos.execute] Listing all todos");

    List<Todo> todos = todoRepository.findAll();

    System.out.println("[ListTodos.execute] Found " + todos.size() + " todos");

    return todos;
  }
}
