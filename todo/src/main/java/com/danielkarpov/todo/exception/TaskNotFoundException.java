package com.danielkarpov.todo.exception;

public class TaskNotFoundException extends RuntimeException {

    private TaskNotFoundException(String reason) {
        super(reason);
    }

    public static TaskNotFoundException causedByEmptyList(){
        return new TaskNotFoundException("Task list is empty");
    }

    public static TaskNotFoundException causedByTaskNonExistence(String name){
        return new TaskNotFoundException(String.format("Task with name= %s was not found", name));
    }
}