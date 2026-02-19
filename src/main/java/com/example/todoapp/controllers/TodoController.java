package com.example.todoapp.controllers;

import com.example.todoapp.dtos.TodoFormDto;
import com.example.todoapp.entities.Todo;
import com.example.todoapp.services.TodoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String list(Model model, @ModelAttribute("message") String message) {
        model.addAttribute("todos", todoService.findAll());
        return "todos";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("todoForm", new TodoFormDto());
        model.addAttribute("mode", "create");
        return "todo-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("todoForm") TodoFormDto form,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "create");
            return "todo-form";
        }

        todoService.create(form);
        redirectAttributes.addFlashAttribute("message", "Todo created successfully.");
        return "redirect:/todos";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Todo todo = todoService.findById(id);

        TodoFormDto form = new TodoFormDto();
        form.setTitle(todo.getTitle());
        form.setDescription(todo.getDescription());
        form.setCompleted(todo.isCompleted());

        model.addAttribute("todoId", id);
        model.addAttribute("todoForm", form);
        model.addAttribute("mode", "edit");
        return "todo-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("todoForm") TodoFormDto form,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todoId", id);
            model.addAttribute("mode", "edit");
            return "todo-form";
        }

        todoService.update(id, form);
        redirectAttributes.addFlashAttribute("message", "Todo updated successfully.");
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        todoService.toggleCompleted(id);
        redirectAttributes.addFlashAttribute("message", "Todo completion toggled.");
        return "redirect:/todos";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        todoService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Todo deleted successfully.");
        return "redirect:/todos";
    }
}
