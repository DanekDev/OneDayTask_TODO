package com.danielkarpov.todo.service;

import com.danielkarpov.todo.model.Dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDto> getAllTasks();

    TaskDto createTask(String name);

    Optional<TaskDto> updateTask(TaskDto taskDto);

    void deleteTask(String taskName);
}
