package com.example.todoapp.services;

import com.example.todoapp.dtos.TodoFormDto;
import com.example.todoapp.entities.Todo;
import com.example.todoapp.entities.User;
import com.example.todoapp.exceptions.TodoNotFoundException;
import com.example.todoapp.repositories.TodoRepository;
import com.example.todoapp.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Todo> findAll(UUID userId) {
        return todoRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Todo findById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    public Todo create(TodoFormDto dto, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = new Todo();
        todo.setUser(user);
        applyDto(todo, dto);
        return todoRepository.save(todo);
    }

    @Override
    public Todo update(Long id, TodoFormDto dto, UUID userId) {
        Todo todo = findByIdAndValidateUser(id, userId);
        applyDto(todo, dto);
        return todoRepository.save(todo);
    }

    @Override
    public void delete(Long id, UUID userId) {
        Todo todo = findByIdAndValidateUser(id, userId);
        todoRepository.delete(todo);
    }

    @Override
    public Todo toggleCompleted(Long id, UUID userId) {
        Todo todo = findByIdAndValidateUser(id, userId);
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    private Todo findByIdAndValidateUser(Long id, UUID userId) {
        Todo todo = findById(id);
        if (!todo.getUser().getId().equals(userId)) {
            throw new TodoNotFoundException(id);
        }
        return todo;
    }

    private void applyDto(Todo todo, TodoFormDto dto) {
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setCompleted(dto.isCompleted());
    }
}
