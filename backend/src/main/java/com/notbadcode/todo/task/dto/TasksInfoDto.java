package com.notbadcode.todo.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TasksInfoDto(long count,
                           @JsonProperty("is_all_completed") boolean isCompleted,
                           @JsonProperty("has_completed") boolean hasCompleted) {
}
