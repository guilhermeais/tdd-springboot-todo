package com.guiais.todo.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.guiais.todo.application.usecases.dtos.UpdateTodoDto;
import com.guiais.todo.domain.entities.Todo;
import com.guiais.todo.mocks.applications.protocols.repositories.InMemoryTodoRepository;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdateTodoTest {

  private static InMemoryTodoRepository todoRepository;
  private static UpdateTodo sut;

  @BeforeAll
  public static void setup() {
    todoRepository = new InMemoryTodoRepository();

    sut = new UpdateTodo(todoRepository);
  }

  @AfterEach
  public void afterEach() {
    todoRepository.clear();
  }

  @Test
  @DisplayName("Should update a todo title")
  public void shouldUpdateTodoTitle() {
    Todo todo = Todo
      .builder()
      .title("My todo")
      .description("My todo description")
      .build();
    todoRepository.save(todo);

    UpdateTodoDto request = UpdateTodoDto
      .builder()
      .title("My todo updated")
      .build();

    sut.execute(todo.getId(), request);

    Todo updatedTodo = todoRepository.findById(todo.getId()).orElse(null);

    assertNotNull(updatedTodo);
    assertNotNull(updatedTodo.getId());
    assertTrue(updatedTodo.getId() instanceof UUID);
    assertEquals("My todo updated", updatedTodo.getTitle());
    assertEquals("My todo description", todo.getDescription());
  }

  @Test
  @DisplayName("Should update a todo description")
  public void shouldUpdateTodoDescription() {
    Todo todo = Todo
      .builder()
      .title("My todo")
      .description("My todo description")
      .build();
    todoRepository.save(todo);

    UpdateTodoDto request = UpdateTodoDto
      .builder()
      .description("My todo description updated")
      .build();

    sut.execute(todo.getId(), request);

    Todo updatedTodo = todoRepository.findById(todo.getId()).orElse(null);

    assertNotNull(updatedTodo);
    assertNotNull(updatedTodo.getId());
    assertTrue(updatedTodo.getId() instanceof UUID);
    assertEquals(todo.getTitle(), updatedTodo.getTitle());
    assertEquals("My todo description updated", updatedTodo.getDescription());
  }

  @Test
  @DisplayName("Should update a todo title and description")
  public void shouldUpdateTodoTitleAndDescription() {
    Todo todo = Todo
      .builder()
      .title("My todo")
      .description("My todo description")
      .build();
    todoRepository.save(todo);

    UpdateTodoDto request = UpdateTodoDto
      .builder()
      .title("My todo updated")
      .description("My todo description updated")
      .build();

    sut.execute(todo.getId(), request);

    Todo updatedTodo = todoRepository.findById(todo.getId()).orElse(null);

    assertNotNull(updatedTodo);
    assertNotNull(updatedTodo.getId());
    assertTrue(updatedTodo.getId() instanceof UUID);
    assertEquals("My todo updated", updatedTodo.getTitle());
    assertEquals("My todo description updated", updatedTodo.getDescription());
  }

  @Test
  @DisplayName("Should throw an exception when todo is not found")
  public void shouldThrowAnExceptionWhenTodoIsNotFound() {
    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        UpdateTodoDto request = UpdateTodoDto
          .builder()
          .title("My todo updated")
          .description("My todo description updated")
          .build();

        sut.execute(UUID.randomUUID(), request);
      }
    );

    assertEquals("Todo not found", exception.getMessage());
  }

  @Test
  @DisplayName("Should not update a todo when title and description are null")
  public void shouldNotUpdateTodoWhenTitleAndDescriptionAreNull() {
    Todo todo = Todo
      .builder()
      .title("My todo")
      .description("My todo description")
      .build();
    todoRepository.save(todo);

    UpdateTodoDto request = UpdateTodoDto.builder().build();

    sut.execute(todo.getId(), request);

    Todo updatedTodo = todoRepository.findById(todo.getId()).orElse(null);

    assertNotNull(updatedTodo);
    assertNotNull(updatedTodo.getId());
    assertTrue(updatedTodo.getId() instanceof UUID);
    assertEquals(todo.getTitle(), updatedTodo.getTitle());
    assertEquals(todo.getDescription(), updatedTodo.getDescription());
  }
}
