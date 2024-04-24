package com.guiais.todo.application.usecases.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTodoDto {

  private String title;
  private String description;
}
