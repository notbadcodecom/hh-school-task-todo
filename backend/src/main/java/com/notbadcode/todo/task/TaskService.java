package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.TaskDto;
import com.notbadcode.todo.task.dto.TasksInfoDto;
import com.notbadcode.todo.task.dto.CreateTaskDto;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class TaskService {

  private final Sort TASK_SORT = Sort.by("created").descending();

  private final TaskRepository repository;

  public TaskService(TaskRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public TaskDto createTask(CreateTaskDto taskDto) {
    Task task = repository.save(TaskMapper.createTaskToTask(taskDto));
    return TaskMapper.taskToTaskDto(task);
  }

  public TaskDto findTaskById(Long id) {
    return TaskMapper.taskToTaskDto(getTaskById(id));
  }

  public List<TaskDto> findAllByFilter(TaskFilter taskFilter) {
    return taskFilter.getCompletedOptional()
        .map(completed -> repository.findAll(isCompleted(completed), TASK_SORT))
        .orElseGet(() -> repository.findAll(TASK_SORT))
        .stream()
        .map(TaskMapper::taskToTaskDto)
        .toList();
  }

  @Transactional
  public List<TaskDto> toggleAll() {
    boolean isAllTasksNotCompleted = repository.existsAllByCompletedIsFalse();
    return repository.findAll(TASK_SORT).stream()
        .map(task -> {
              task.setCompleted(isAllTasksNotCompleted);
              return TaskMapper.taskToTaskDto(task);
            })
        .toList();
  }

  @Transactional
  public TaskDto toggleTaskById(Long id) {
    Task task = getTaskById(id);
    task.setCompleted(!task.getCompleted());
    return TaskMapper.taskToTaskDto(task);
  }

  @Transactional
  public TaskDto updateTask(TaskDto taskDto) {
   Task task = getTaskById(taskDto.id());
    if (taskDto.title() != null && !taskDto.title().isBlank()) {
      task.setTitle(taskDto.title());
    }
    if (taskDto.completed() != null) {
      task.setCompleted(taskDto.completed());
    }
    return TaskMapper.taskToTaskDto(task);
  }

  public TasksInfoDto getTasksInfo() {
    long countCompleted = repository.count(isCompleted(true));
    long countActive = repository.count(isCompleted(false));
    long countAll = countCompleted + countActive;
    boolean hasActive = repository.existsTaskByCompletedIsFalse();
    boolean hasCompleted = repository.existsTaskByCompletedIsTrue();
    return new TasksInfoDto(countAll, countCompleted, countActive, hasActive, hasCompleted);
  }

  @Transactional
  public void deleteTaskById(Long id) {
    repository.delete(getTaskById(id));
  }

  @Transactional
  public void deleteCompletedTasks() {
    List<Task> completedTasks = repository.findAll(isCompleted(true));
    repository.deleteAll(completedTasks);
  }

  private Task getTaskById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("task #" + id + " was not found"));
  }

  private Specification<Task> isCompleted(boolean completed) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("completed"), completed);
  }

}
