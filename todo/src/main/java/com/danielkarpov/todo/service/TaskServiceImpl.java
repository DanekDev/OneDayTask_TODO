package com.danielkarpov.todo.service;

import com.danielkarpov.todo.exception.TaskNotFoundException;
import com.danielkarpov.todo.model.Dto.TaskDto;
import com.danielkarpov.todo.persistence.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class
TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDto> getAllTasks(){
        return taskRepository.getAllTasks()
                .orElseThrow(TaskNotFoundException::causedByEmptyList);
    }

    @Override
    public TaskDto createTask(String name){
        return taskRepository.add(new TaskDto(name, true));
    }

    @Override
    public Optional<TaskDto> updateTask(TaskDto taskDto){
        Optional<TaskDto> updatedTask = taskRepository.updateTask(taskDto);
        return updatedTask;
    }

    @Override
    public void deleteTask(String taskName){
        taskRepository.safelyDeleteTask(taskName);
    }
}
