package com.guiais.todo.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.entities.enums.TodoStatus;
import com.guiais.todo.mocks.applications.protocols.repositories.InMemoryTodoRepository;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompleteTodoTest {

  private static InMemoryTodoRepository todoRepository;
  private static CompleteTodo sut;

  @BeforeAll
  public static void setup() {
    todoRepository = new InMemoryTodoRepository();

    sut = new CompleteTodo(todoRepository);
  }

  @AfterEach
  public void afterEach() {
    todoRepository.clear();
  }

  @Test
  @DisplayName("Should complete an existing todo")
  public void shouldCompleteAnExistingTodo() {
    UUID todoId = UUID.randomUUID();
    Todo todo = Todo
      .builder()
      .id(todoId)
      .title("My todo")
      .description("My todo description")
      .build();
    todoRepository.save(todo);

    sut.execute(todoId);

    Todo completedTodo = todoRepository.findById(todoId).orElseGet(null);

    assertNotNull(completedTodo);
    assertEquals(TodoStatus.DONE, completedTodo.getStatus());
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
  @DisplayName("Should throw an exception when todo is not found")
  public void shouldThrowAnExceptionWhenTodoIsNotFound() {
    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        sut.execute(UUID.randomUUID());
      }
    );

    assertEquals("Todo not found", exception.getMessage());
  }

  @Test
  @DisplayName("Should throw an exception when todo is already completed")
  public void shouldThrowAnExceptionWhenTodoIsAlreadyCompleted() {
    UUID todoId = UUID.randomUUID();
    Todo todo = Todo
      .builder()
      .id(todoId)
      .title("My todo")
      .description("My todo description")
      .status(TodoStatus.DONE)
      .build();
    todoRepository.save(todo);

    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        sut.execute(todoId);
      }
    );

    assertEquals("Todo is already done", exception.getMessage());
  }
}
