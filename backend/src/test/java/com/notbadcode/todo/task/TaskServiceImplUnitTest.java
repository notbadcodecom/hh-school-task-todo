package com.notbadcode.todo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notbadcode.todo.common.JsonMapper;
import com.notbadcode.todo.task.dto.TaskDto;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplUnitTest {

  @Mock
  private TaskRepository repository;

  private TaskService service;

  @BeforeEach
  void setUp() {
    JsonMapper<Task> jsonMapper = new JsonMapper<>(new ObjectMapper());
    service = new TaskServiceImpl(repository, jsonMapper);
  }

  @Test
  @DisplayName("Создание задачи")
  void createTask_thenReturnTaskDto() {
    // Arrange
    Mockito.when(repository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    String testText = "test text";
    String taskJson = "{\"title\": \"" + testText + "\"}";

    //Act
    TaskDto task = service.createTaskFromString(taskJson);

    // Asserts
    assertThat(task)
        .isNotNull()
        .hasFieldOrProperty("id")
        .hasFieldOrPropertyWithValue("title", testText)
        .hasFieldOrProperty("completed");
  }

  @Test
  @DisplayName("Создание задачи с ошибкой (без заголовка)")
  void createTaskWithoutTitle_thenThrowException() {
    // Arrange
    String taskJson = "{\"title\": \"   \"}";
    String exceptionMessage = "title is required";

    //Act
    BadRequestException exception = assertThrows(
        BadRequestException.class,
        () -> service.createTaskFromString(taskJson),
        exceptionMessage
    );

    // Asserts
    assertThat(exception).hasMessage(exceptionMessage);
  }

  @Test
  @DisplayName("Поиск задачи по ID")
  void findTaskById_thenReturnTaskDto() {
    // Arrange
    Mockito.when(repository.findById(any()))
        .thenReturn(Optional.of(Mockito.mock(Task.class)));

    //Act
    TaskDto task = service.findTaskById(anyLong());

    // Asserts
    assertThat(task)
        .isNotNull()
        .hasFieldOrProperty("id")
        .hasFieldOrProperty("title")
        .hasFieldOrProperty("completed");
  }

  @Test
  @DisplayName("Поиск несуществующей задачи по ID")
  void findTaskByWrongId_thenThrowException() {
    // Arrange
    long id = 42L;
    String exceptionMessage = "task #" + id + " was not found";
    Mockito.when(repository.findById(id))
        .thenReturn(Optional.empty());

    //Act
    NotFoundException exception = assertThrows(
        NotFoundException.class,
        () -> service.findTaskById(id),
        exceptionMessage
    );

    // Asserts
    assertThat(exception).hasMessage(exceptionMessage);
  }
}
