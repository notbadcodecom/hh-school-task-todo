package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.TaskDto;
import com.notbadcode.todo.task.dto.TasksInfoDto;

import java.util.List;

public interface TaskService {

  TaskDto findTaskById(Long id);

  List<TaskDto> findAllByFilter(String filter);

  TasksInfoDto getTasksInfo(String filter);

  TaskDto createTaskFromString(String json);

  TaskDto updateTaskFromString(String json);

  List<TaskDto> toggleAll();

  TaskDto toggleTaskById(Long id);

  void deleteTaskById(Long id);

  void deleteCompletedTasks();

}
