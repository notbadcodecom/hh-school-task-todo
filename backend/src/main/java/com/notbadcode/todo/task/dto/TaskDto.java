package com.notbadcode.todo.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskDto(@NotNull(message = "id is required") Long id,
                      @NotBlank(message = "title is required") String title,
                      Boolean completed) {
}
