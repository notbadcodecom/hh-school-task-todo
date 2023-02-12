package com.notbadcode.todo.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks", schema = "public")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "completed")
  private Boolean completed = false;

  @CreationTimestamp
  @Column(name = "created")
  private LocalDateTime created;

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  @Override
  public String toString() {
    return "Task{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", completed=" + completed +
        '}';
  }
}
