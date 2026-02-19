package com.example.todoapp.services;

import com.example.todoapp.dtos.TodoFormDto;
import com.example.todoapp.entities.Todo;

import java.util.List;

public interface TodoService {

    List<Todo> findAll();

    Todo findById(Long id);

    Todo create(TodoFormDto dto);

    Todo update(Long id, TodoFormDto dto);

    void delete(Long id);

    Todo toggleCompleted(Long id);
}
