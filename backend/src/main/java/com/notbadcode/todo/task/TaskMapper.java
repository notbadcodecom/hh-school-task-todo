package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.TaskDto;

public class TaskMapper {

  private TaskMapper() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static TaskDto toTaskDto (Task task) {
    return new TaskDto(task.getId(), task.getTitle(), task.getCompleted());
  }

}
