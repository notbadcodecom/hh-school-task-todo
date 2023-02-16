package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.CreateTaskDto;
import com.notbadcode.todo.task.dto.TaskDto;
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
class TaskServiceUnitTest {

  @Mock
  private TaskRepository repository;

  private TaskService service;

  @BeforeEach
  void setUp() {
    service = new TaskService(repository);
  }

  @Test
  @DisplayName("Создание задачи")
  void createTask_thenReturnTaskDto() {
    // Arrange
    Mockito.when(repository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    CreateTaskDto taskDto = new CreateTaskDto("test text");

    //Act
    TaskDto createdTask = service.createTask(taskDto);

    // Asserts
    assertThat(createdTask)
        .isNotNull()
        .hasFieldOrProperty("id")
        .hasFieldOrPropertyWithValue("title", taskDto.title())
        .hasFieldOrProperty("completed");
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
