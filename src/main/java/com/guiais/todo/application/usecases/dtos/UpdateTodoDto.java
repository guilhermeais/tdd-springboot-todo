package com.guiais.todo.application.usecases.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class UpdateTodoDto {

  private String title;
  private String description;

  public UpdateTodoDto() {}

  public UpdateTodoDto(String title, String description) {
    this.title = title;
    this.description = description;
  }
}
