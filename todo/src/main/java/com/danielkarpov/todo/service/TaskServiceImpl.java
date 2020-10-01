package com.danielkarpov.todo.service;

import com.danielkarpov.todo.exception.TaskNotFoundException;
import com.danielkarpov.todo.model.Dao.tables.pojos.Task;
import com.danielkarpov.todo.model.Dto.TaskDto;
import com.danielkarpov.todo.persistence.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public TaskDto updateTask(TaskDto taskDto){
        TaskDto updatedTask = taskRepository.updateTask(taskDto);
        return updatedTask;
    }

    @Override
    public void deleteTask(String taskName){
        taskRepository.deleteTask(taskName);
    }
}
