package com.notbadcode.todo.task.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskDto(@NotBlank(message = "title is required") String title) {
}
