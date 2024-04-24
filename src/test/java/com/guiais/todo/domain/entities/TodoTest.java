package com.guiais.todo.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.guiais.todo.domain.entities.enums.TodoStatus;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TodoTest {

  @Test
  @DisplayName("Should create a todo")
  public void shouldCreateATodo() {
    Todo todo = Todo
      .builder()
      .title("aTitle")
      .description("aDescription")
      .build();

    assertEquals("aTitle", todo.getTitle());
    assertEquals("aDescription", todo.getDescription());
    assertEquals(TodoStatus.PENDING, todo.getStatus());
    assertNotNull(todo.getCreatedAt());
    assertTrue(todo.getCreatedAt() instanceof LocalDate);

    assertNotNull(todo.getId());
    assertTrue(todo.getId() instanceof UUID);
  }
}
