package com.notbadcode.todo.task.dto;

public class TaskDto {

  private final Long id;

  private final String title;

  private final boolean completed;

  public TaskDto(Long id, String title, boolean completed) {
    this.id = id;
    this.title = title;
    this.completed = completed;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Boolean getCompleted() {
    return completed;
  }

}
