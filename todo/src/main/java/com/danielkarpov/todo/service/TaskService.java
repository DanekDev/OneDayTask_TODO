package com.danielkarpov.todo.service;

import com.danielkarpov.todo.model.Dao.tables.pojos.Task;
import com.danielkarpov.todo.model.Dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();

    TaskDto createTask(String name);

    TaskDto updateTask(TaskDto taskDto);

    void deleteTask(String taskName);
}
