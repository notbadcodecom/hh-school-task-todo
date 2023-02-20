package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.CreateTaskDto;
import com.notbadcode.todo.task.dto.TaskDto;
import com.notbadcode.todo.task.dto.TasksInfoDto;
import jakarta.ws.rs.NotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Primary
@Transactional(readOnly = true)
public class TaskServiceDtoImpl implements TaskService {

  private final TaskDao taskDao;

  public TaskServiceDtoImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  @Transactional
  public TaskDto createTask(CreateTaskDto taskDto) {
    Task task = taskDao.save(TaskMapper.createTaskToTask(taskDto));
    return TaskMapper.taskToTaskDto(task);
  }

  @Override
  public TaskDto findTaskById(Long id) {
    return TaskMapper.taskToTaskDto(getTaskById(id));
  }

  @Override
  public List<TaskDto> findAllByFilter(TaskFilter taskFilter) {
    return taskFilter.getCompletedOptional()
        .map(taskDao::findAll)
        .orElseGet(taskDao::findAll)
        .stream()
        .map(TaskMapper::taskToTaskDto)
        .toList();
  }

  @Override
  @Transactional
  public List<TaskDto> toggleAll() {
    boolean isCompleted = taskDao.existActive();
    return taskDao.findAll().stream()
        .map(task -> {
              task.setCompleted(isCompleted);
              return TaskMapper.taskToTaskDto(task);
            })
        .toList();
  }

  @Override
  @Transactional
  public TaskDto toggleTaskById(Long id) {
    Task task = getTaskById(id);
    task.setCompleted(!task.getCompleted());
    return TaskMapper.taskToTaskDto(task);
  }

  @Override
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

  @Override
  public TasksInfoDto getTasksInfo() {
    long countCompleted = taskDao.count(true);
    long countActive = taskDao.count(false);
    long countAll = countCompleted + countActive;
    boolean hasActive = countActive > 0;
    boolean hasCompleted = countCompleted > 0;
    return new TasksInfoDto(countAll, countCompleted, countActive, hasActive, hasCompleted);
  }

  @Override
  @Transactional
  public void deleteTaskById(Long id) {
    taskDao.delete(getTaskById(id));
  }

  @Override
  @Transactional
  public void deleteCompletedTasks() {
    List<Task> completedTasks = taskDao.findAll(true);
    taskDao.deleteAll(completedTasks);
  }

  private Task getTaskById(Long id) {
    return taskDao.findById(id)
        .orElseThrow(() -> new NotFoundException("task #" + id + " was not found"));
  }

}
