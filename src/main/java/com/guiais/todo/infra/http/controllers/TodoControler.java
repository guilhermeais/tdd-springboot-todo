package com.guiais.todo.infra.http.controllers;

import com.guiais.todo.application.usecases.CompleteTodo;
import com.guiais.todo.application.usecases.CreateTodo;
import com.guiais.todo.application.usecases.DeleteTodo;
import com.guiais.todo.application.usecases.ListTodos;
import com.guiais.todo.application.usecases.UpdateTodo;
import com.guiais.todo.application.usecases.dtos.CreateTodoDto;
import com.guiais.todo.application.usecases.dtos.UpdateTodoDto;
import com.guiais.todo.domain.entities.Todo;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoControler {

  private final ListTodos listTodos;
  private final CreateTodo createTodo;
  private final DeleteTodo deleteTodo;
  private final UpdateTodo updateTodo;
  private final CompleteTodo completeTodo;

  @GetMapping
  public ResponseEntity list() {
    try {
      System.out.println("[TodoControler.list] Listing all todos");
      List<Todo> todos = listTodos.execute();

      System.out.println(
        "[TodoControler.list] Found " + todos.size() + " todos"
      );
      return ResponseEntity.ok(todos);
    } catch (Exception e) {
      System.out.println("[TodoControler.list] Error: " + e.getMessage());

      return ResponseEntity.internalServerError().body("Server Error");
    }
  }

  @PostMapping
  public ResponseEntity create(@RequestBody CreateTodoDto dto) {
    try {
      System.out.println("[TodoControler.create] Creating a new todo");
      Todo todo = createTodo.execute(dto);
      System.out.println(
        "[TodoControler.create] Todo [" +
        todo.getId() +
        "] - " +
        todo.getTitle() +
        " created"
      );
      return ResponseEntity.ok(todo);
    } catch (Exception e) {
      System.out.println("[TodoControler.create] Error: " + e.getMessage());
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity delete(@PathVariable("id") UUID id) {
    try {
      System.out.println("[TodoControler.delete] Deleting todo [" + id + "]");
      deleteTodo.execute(id);
      System.out.println("[TodoControler.delete] Todo [" + id + "] deleted");
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      System.out.println("[TodoControler.delete] Error: " + e.getMessage());
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @PatchMapping("{id}")
  public ResponseEntity update(
    @PathVariable("id") UUID id,
    @RequestBody UpdateTodoDto dto
  ) {
    try {
      System.out.println("[TodoControler.update] Updating todo [" + id + "]");
      updateTodo.execute(id, dto);
      System.out.println(
        "[TodoControler.update] Todo [" + id.toString() + " updated"
      );
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      System.out.println("[TodoControler.update] Error: " + e.getMessage());
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @PatchMapping("{id}/complete")
  public ResponseEntity complete(@PathVariable("id") UUID id) {
    try {
      System.out.println(
        "[TodoControler.complete] Completing todo [" + id + "]"
      );
      completeTodo.execute(id);
      System.out.println(
        "[TodoControler.complete] Todo [" + id + "] completed"
      );
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      System.out.println("[TodoControler.complete] Error: " + e.getMessage());
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }
}
