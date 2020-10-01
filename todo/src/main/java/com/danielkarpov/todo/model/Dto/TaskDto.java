package com.danielkarpov.todo.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {
    private String name;
    private boolean status;
}
