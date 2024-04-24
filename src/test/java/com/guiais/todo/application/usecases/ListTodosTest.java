package com.guiais.todo.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.mocks.applications.protocols.repositories.InMemoryTodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListTodosTest {

  private static InMemoryTodoRepository todoRepository;
  private static ListTodos sut;

  @BeforeAll
  public static void setup() {
    todoRepository = new InMemoryTodoRepository();

    sut = new ListTodos(todoRepository);
  }

  @AfterEach
  public void afterEach() {
    todoRepository.clear();
  }

  @Test
  @DisplayName("Should list all todos")
  public void shouldListAllTodos() {
    Todo todo1 = Todo
      .builder()
      .title("My todo 1")
      .description("My todo description 1")
      .build();
    todoRepository.save(todo1);

    Todo todo2 = Todo
      .builder()
      .title("My todo 2")
      .description("My todo description 2")
      .build();
    todoRepository.save(todo2);

    Todo todo3 = Todo
      .builder()
      .title("My todo 3")
      .description("My todo description 3")
      .build();
    todoRepository.save(todo3);

    var todos = sut.execute();

    assertNotNull(todos);
    assertEquals(3, todos.size());
  }
}
