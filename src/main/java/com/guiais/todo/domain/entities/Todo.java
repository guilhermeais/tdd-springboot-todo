package com.guiais.todo.domain.entities;

import com.guiais.todo.domain.entities.enums.TodoStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Entity
@Table(name = "todos", schema = "todoapp")
@Data
@Getter
@Setter
@AllArgsConstructor
public class Todo {

  public Todo() {}

  @Builder
  public static Todo create(
    UUID id,
    String title,
    String description,
    TodoStatus status,
    String observation,
    LocalDate createdAt,
    LocalDate updatedAt
  ) {
    Todo todo = new Todo();
    todo.id = id == null ? UUID.randomUUID() : id;
    todo.title = title;
    todo.description = description;
    todo.status = status == null ? TodoStatus.PENDING : status;
    todo.observation = observation;
    todo.createdAt = createdAt == null ? LocalDate.now() : createdAt;
    todo.updatedAt = updatedAt;
    return todo;
  }

  @Id
  @Column(columnDefinition = "uuid", updatable = false)
  private UUID id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "status", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private TodoStatus status = TodoStatus.PENDING;

  @Column(name = "observation")
  private String observation;

  @Column(name = "createdAt", nullable = false)
  @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
  private LocalDate createdAt = LocalDate.now();

  @Column(name = "updatedAt")
  @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
  private LocalDate updatedAt;

  public Boolean isPending() {
    return this.status.equals(TodoStatus.PENDING);
  }

  public Boolean isDone() {
    return this.status.equals(TodoStatus.DONE);
  }

  public void moveToDone() {
    if (this.isDone()) {
      throw new IllegalArgumentException("Todo is already done");
    }
    this.status = TodoStatus.DONE;
  }
}
