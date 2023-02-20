package com.notbadcode.todo.task;

import java.util.Optional;

public enum TaskFilter {

  ACTIVE(false),
  COMPLETED(true),
  ALL(null);

  private Boolean completed;

  TaskFilter(Boolean completed) {
    this.completed = completed;
  }

  public Optional<Boolean> getCompletedOptional() {
    return Optional.ofNullable(completed);
  }

}
