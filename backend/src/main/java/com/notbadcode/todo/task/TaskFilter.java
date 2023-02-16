package com.notbadcode.todo.task;

import java.util.Optional;

public enum TaskFilter {
  ACTIVE,
  COMPLETED,
  ALL;

  public Optional<Boolean> getCompletedOptional() {
    return switch (this) {
      case ACTIVE -> Optional.of(false);
      case COMPLETED -> Optional.of(true);
      default -> Optional.empty();
    };
  }

}
