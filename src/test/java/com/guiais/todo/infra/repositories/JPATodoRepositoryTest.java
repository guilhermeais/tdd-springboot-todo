package com.guiais.todo.infra.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.guiais.todo.domain.entities.Todo;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class JPATodoRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  JPATodoRepository jpaTodoRepository;

  @BeforeEach
  public void setup() {
    entityManager.clear();
  }

  @Test
  @Description("[save()] Should save a todo")
  public void save_shouldSaveATodo() {
    Todo todo = createTodo();

    jpaTodoRepository.save(todo);

    Todo foundTodo = entityManager.find(Todo.class, todo.getId());

    assertNotNull(foundTodo);
    assertEquals(todo, foundTodo);
  }

  @Test
  @Description("[findById()] Should get an existing todo")
  public void findById_shouldGetAnExistingTodo() {
    Todo todo = createTodo();

    Todo foundTodo = jpaTodoRepository.findById(todo.getId()).orElse(null);

    assertNotNull(foundTodo);
    assertEquals(todo, foundTodo);
  }

  @Test
  @Description("[findAll()] Should get all todos")
  public void findAll_shouldGetAllTodos() {
    Todo fisrt = createTodo();
    Todo second = createTodo();

    List<Todo> todos = jpaTodoRepository.findAll();

    assertNotNull(todos);
    assertEquals(2, todos.size());
    assertEquals(fisrt, todos.get(0));
    assertEquals(second, todos.get(1));
  }

  @Test
  @Description("[deleteById()] Should delete an existing todo")
  public void deleteById_shouldDeleteAnExistingTodo() {
    Todo todo = createTodo();

    Todo foundTodo = jpaTodoRepository.findById(todo.getId()).orElse(null);

    assertNotNull(foundTodo);

    jpaTodoRepository.deleteById(todo.getId());

    foundTodo = jpaTodoRepository.findById(todo.getId()).orElse(null);

    assertEquals(null, foundTodo);
  }

  private Todo createTodo() {
    Todo todo = Todo
      .builder()
      .title("My todo")
      .description("My todo description")
      .build();

    jpaTodoRepository.save(todo);

    return todo;
  }
}
