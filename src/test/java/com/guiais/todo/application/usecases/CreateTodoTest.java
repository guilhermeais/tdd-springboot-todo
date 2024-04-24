package com.guiais.todo.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.guiais.todo.application.usecases.dtos.CreateTodoDto;
import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.domain.entities.enums.TodoStatus;
import com.guiais.todo.mocks.applications.protocols.repositories.InMemoryTodoRepository;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateTodoTest {

  private static InMemoryTodoRepository todoRepository;
  private static CreateTodo sut;

  @BeforeAll
  public static void setup() {
    todoRepository = new InMemoryTodoRepository();

    sut = new CreateTodo(todoRepository);
  }

  @AfterEach
  public void afterEach() {
    todoRepository.clear();
  }

  @Test
  @DisplayName("Should create a todo")
  public void shouldCreateATodo() {
    CreateTodoDto dto = CreateTodoDto
      .builder()
      .title("My todo")
      .description("My todo description")
      .build();
    Todo todo = sut.execute(dto);
    UUID todoId = todo.getId();
    Todo insertedTodo = todoRepository.findById(todoId).orElseGet(null);

    assertNotNull(insertedTodo);
    assertNotNull(insertedTodo.getId());
    assertTrue(insertedTodo.getId() instanceof UUID);
    assertEquals("My todo", insertedTodo.getTitle());
    assertEquals("My todo description", insertedTodo.getDescription());
    assertEquals(TodoStatus.PENDING, insertedTodo.getStatus());
    assertNotNull(insertedTodo.getCreatedAt());
    assertTrue(insertedTodo.getCreatedAt() instanceof LocalDate);
  }

  @Test
  @DisplayName("Should throw an exception when title is null")
  public void shouldThrowAnExceptionWhenTitleIsNull() {
    CreateTodoDto dto = CreateTodoDto
      .builder()
      .description("My todo description")
      .build();
    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        sut.execute(dto);
      }
    );

    assertEquals("Title is required", exception.getMessage());
  }

  @Test
  @DisplayName("Should throw an exception when description is null")
  public void shouldThrowAnExceptionWhenDescriptionIsNull() {
    CreateTodoDto dto = CreateTodoDto.builder().title("My todo").build();
    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        sut.execute(dto);
      }
    );

    assertEquals("Description is required", exception.getMessage());
  }
}
