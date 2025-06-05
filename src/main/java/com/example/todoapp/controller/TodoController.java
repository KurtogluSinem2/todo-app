package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public String listTodos(@RequestParam(required = false) String editId, Model model) {
        model.addAttribute("todos", service.findAll());
        model.addAttribute("editId", editId); // Ermöglicht das Editieren über das Frontend
        return "todos";
    }

    @PostMapping
    public String addTodo(@ModelAttribute Todo todo) {
        System.out.println("POST empfangen: " + todo.getTitle() + ", erledigt: " + todo.isCompleted());
        service.save(todo);
        return "redirect:/todos";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/todos";
    }

    @GetMapping("/toggle/{id}")
    public String toggleTodo(@PathVariable Long id) {
        Todo todo = service.findById(id);
        if (todo != null) {
            todo.setCompleted(!todo.isCompleted());
            service.save(todo);
        }
        return "redirect:/todos";
    }

    @PostMapping("/update")
    public String updateTodo(@RequestParam Long id, @RequestParam String title) {
        Todo todo = service.findById(id);
        if (todo != null) {
            todo.setTitle(title);
            service.save(todo);
        }
        return "redirect:/todos";
    }

    @GetMapping("/")
    public String redirectToTodos() {
        return "redirect:/todos";
    }
}
