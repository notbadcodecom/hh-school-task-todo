package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.CreateTaskDto;
import com.notbadcode.todo.task.dto.TaskDto;

public class TaskMapper {

  private TaskMapper() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static TaskDto taskToTaskDto(Task task) {
    return new TaskDto(task.getId(), task.getTitle(), task.getCompleted());
  }

  public static Task createTaskToTask(CreateTaskDto task) {
    return new Task(task.title());
  }

}
