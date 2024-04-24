package com.guiais.todo.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.mocks.applications.protocols.repositories.InMemoryTodoRepository;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteTodoTest {

  private static InMemoryTodoRepository todoRepository;
  private static DeleteTodo sut;

  @BeforeAll
  public static void setup() {
    todoRepository = new InMemoryTodoRepository();

    sut = new DeleteTodo(todoRepository);
  }

  @AfterEach
  public void afterEach() {
    todoRepository.clear();
  }

  @Test
  @DisplayName("Should delete an existing todo")
  public void shouldDeleteAnExistingTodo() {
    UUID todoId = UUID.randomUUID();
    Todo todo = Todo
      .builder()
      .id(todoId)
      .title("My todo")
      .description("My todo description")
      .build();
    todoRepository.save(todo);

    sut.execute(todoId);

    assertTrue(todoRepository.findById(todoId).isEmpty());
  }

  @Test
  @DisplayName("Should throw an exception when id is null")
  public void shouldThrowAnExceptionWhenIdIsNull() {
    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        sut.execute(null);
      }
    );

    assertEquals("id is required", exception.getMessage());
  }

  @Test
  @DisplayName("Should throw an exception when todo not found")
  public void shouldThrowAnExceptionWhenTodoIsNotFound() {
    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        sut.execute(UUID.randomUUID());
      }
    );

    assertEquals("Todo not found", exception.getMessage());
  }
}
