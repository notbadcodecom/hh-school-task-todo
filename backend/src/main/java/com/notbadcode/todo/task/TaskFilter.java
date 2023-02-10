package com.notbadcode.todo.task;

import jakarta.ws.rs.BadRequestException;

public enum TaskFilter {
  ACTIVE,
  COMPLETED,
  ALL;

  public static TaskFilter fromString(String filter) {
    for (TaskFilter taskFilter : TaskFilter.values()) {
      if (taskFilter.name().equalsIgnoreCase(filter)) {
        return taskFilter;
      }
    }
    throw new BadRequestException("available filters: active, completed, all");
  }
}
