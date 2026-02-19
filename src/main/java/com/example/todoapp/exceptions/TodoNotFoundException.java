package com.example.todoapp.exceptions;

public class TodoNotFoundException extends RuntimeException {
  public TodoNotFoundException(Long id) {
    super("Todo not found: " + id);
  }
}
