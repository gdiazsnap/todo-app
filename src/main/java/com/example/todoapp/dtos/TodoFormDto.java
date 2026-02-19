package com.example.todoapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TodoFormDto {

  @NotBlank
  @Size(max = 120)
  private String title;

  @Size(max = 1000)
  private String description;

  private boolean completed;

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public boolean isCompleted() { return completed; }
  public void setCompleted(boolean completed) { this.completed = completed; }
}
