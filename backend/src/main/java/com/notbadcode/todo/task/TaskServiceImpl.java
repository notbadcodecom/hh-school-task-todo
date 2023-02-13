package com.notbadcode.todo.task;

import com.notbadcode.todo.common.JsonMapper;
import com.notbadcode.todo.task.dto.TaskDto;
import com.notbadcode.todo.task.dto.TasksInfoDto;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

  private final Sort TASK_SORT = Sort.by("created").descending();

  private final JsonMapper<Task> jsonMapper;

  private final TaskRepository repository;

  @Autowired
  public TaskServiceImpl(TaskRepository repository,
                         JsonMapper<Task> jsonMapper) {
    this.repository = repository;
    this.jsonMapper = jsonMapper;
  }

  @Override
  @Transactional
  public TaskDto createTaskFromString(String json) {
    Task task = jsonMapper.fromJson(json, Task.class);
    if (task.getTitle().isBlank()) {
      throw new BadRequestException("title is required");
    }
    repository.save(task);
    return TaskMapper.toTaskDto(task);
  }

  @Override
  public TaskDto findTaskById(Long id) {
    return TaskMapper.toTaskDto(getTaskById(id));
  }

  @Override
  public List<TaskDto> findAllByFilter(String filter) {
    TaskFilter taskFilter = TaskFilter.fromString(filter);
    List<Task> tasks = switch (taskFilter) {
      case ACTIVE -> repository.findAll(isCompleted(false), TASK_SORT);
      case COMPLETED -> repository.findAll(isCompleted(true), TASK_SORT);
      default -> repository.findAll(TASK_SORT);
    };
    return tasks.stream().map(TaskMapper::toTaskDto).toList();
  }

  @Override
  @Transactional
  public List<TaskDto> toggleAll() {
    boolean isAllTasksCompleted = repository.existsAllByCompletedIsFalse();
    return repository.findAll(TASK_SORT).stream()
        .map(task -> {
              task.setCompleted(isAllTasksCompleted);
              return TaskMapper.toTaskDto(task);
            })
        .toList();
  }

  @Override
  @Transactional
  public TaskDto toggleTaskById(Long id) {
    Task task = getTaskById(id);
    task.setCompleted(!task.getCompleted());
    return TaskMapper.toTaskDto(task);
  }

  @Override
  @Transactional
  public TaskDto updateTaskFromString(String json) {
    Task newTask = jsonMapper.fromJson(json, Task.class);
    Task task = getTaskById(newTask.getId());
    if (newTask.getTitle() != null && !newTask.getTitle().isBlank()) {
      task.setTitle(newTask.getTitle());
    }
    if (newTask.getCompleted() != null) {
      task.setCompleted(newTask.getCompleted());
    }
    return TaskMapper.toTaskDto(task);
  }

  @Override
  public TasksInfoDto getTasksInfo(String filter) {
    TaskFilter taskFilter = TaskFilter.fromString(filter);
    return switch (taskFilter) {
      case ACTIVE -> buildCommonTasksInfo(repository.count(isCompleted(false)));
      case COMPLETED -> buildCommonTasksInfo(repository.count(isCompleted(true)));
      default -> buildCommonTasksInfo(repository.count());
    };
  }

  @Override
  @Transactional
  public void deleteTaskById(Long id) {
    repository.delete(getTaskById(id));
  }

  @Override
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

  private TasksInfoDto buildCommonTasksInfo(long count) {
    return new TasksInfoDto(count,
        repository.existsAllByCompletedIsTrue(),
        repository.existsTaskByCompletedIsTrue());
  }
  
}
