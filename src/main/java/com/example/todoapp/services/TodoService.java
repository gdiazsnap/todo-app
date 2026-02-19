package com.example.todoapp.services;

import com.example.todoapp.dtos.TodoFormDto;
import com.example.todoapp.entities.Todo;

import java.util.List;
import java.util.UUID;

public interface TodoService {

    List<Todo> findAll(UUID userId);

    Todo findById(Long id);

    Todo create(TodoFormDto dto, UUID userId);

    Todo update(Long id, TodoFormDto dto, UUID userId);

    void delete(Long id, UUID userId);

    Todo toggleCompleted(Long id, UUID userId);
}
