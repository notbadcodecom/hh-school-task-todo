package com.notbadcode.todo.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TasksInfoDto(@JsonProperty("count_all") long countAll,
                           @JsonProperty("count_completed") long countCompleted,
                           @JsonProperty("count_active") long countActive,
                           @JsonProperty("has_active") boolean hasActive,
                           @JsonProperty("has_completed") boolean hasCompleted) {
}
