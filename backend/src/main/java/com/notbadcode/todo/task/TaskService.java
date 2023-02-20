package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.CreateTaskDto;
import com.notbadcode.todo.task.dto.TaskDto;
import com.notbadcode.todo.task.dto.TasksInfoDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskService {

  TaskDto createTask(CreateTaskDto taskDto);

  TaskDto findTaskById(Long id);

  List<TaskDto> findAllByFilter(TaskFilter taskFilter);

  List<TaskDto> toggleAll();

  @Transactional
  TaskDto toggleTaskById(Long id);

  TaskDto updateTask(TaskDto taskDto);

  TasksInfoDto getTasksInfo();

  void deleteTaskById(Long id);

  void deleteCompletedTasks();

}
