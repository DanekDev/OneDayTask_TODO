package com.danielkarpov.todo.controller;

import com.danielkarpov.todo.model.Dto.CreateTaskDto;
import com.danielkarpov.todo.model.Dto.TaskDto;
import com.danielkarpov.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("change_status")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Optional<TaskDto> optionalTaskDto = taskService.updateTask(taskDto);
        if (optionalTaskDto.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Void> deleteTask(@PathVariable String name) {
        taskService.deleteTask(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto createTaskDto) {
        TaskDto taskDto = taskService.createTask(createTaskDto.getName());
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
}
