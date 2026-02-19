package com.example.todoapp.services;

import com.example.todoapp.dtos.TodoFormDto;
import com.example.todoapp.entities.Todo;
import com.example.todoapp.exceptions.TodoNotFoundException;
import com.example.todoapp.repositories.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Todo findById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    public Todo create(TodoFormDto dto) {
        Todo todo = new Todo();
        applyDto(todo, dto);
        return todoRepository.save(todo);
    }

    @Override
    public Todo update(Long id, TodoFormDto dto) {
        Todo todo = findById(id);
        applyDto(todo, dto);
        return todoRepository.save(todo);
    }

    @Override
    public void delete(Long id) {
        Todo todo = findById(id);
        todoRepository.delete(todo);
    }

    @Override
    public Todo toggleCompleted(Long id) {
        Todo todo = findById(id);
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    private void applyDto(Todo todo, TodoFormDto dto) {
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setCompleted(dto.isCompleted());
    }
}
