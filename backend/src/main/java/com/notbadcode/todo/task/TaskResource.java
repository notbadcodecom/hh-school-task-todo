package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.TaskDto;
import com.notbadcode.todo.task.dto.TasksInfoDto;
import com.notbadcode.todo.task.dto.CreateTaskDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Path("/tasks")
public class TaskResource {

  private final TaskService taskService;

  private final Logger log;

  public TaskResource(TaskService taskService) {
    this.taskService = taskService;
    this.log = LoggerFactory.getLogger(TaskResource.class);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public TaskDto getTaskById(@PathParam("id") Long id,
                              @Context HttpServletRequest request) {
    logUrlInfo(request);
    return taskService.findTaskById(id);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<TaskDto> getAllByFilter(@QueryParam("filter") @DefaultValue("ALL") TaskFilter taskFilter,
                                 @Context HttpServletRequest request) {
    logUrlInfo(request);
    return taskService.findAllByFilter(taskFilter);
  }

  @GET
  @Path("/info")
  @Produces(MediaType.APPLICATION_JSON)
  public TasksInfoDto getTasksInfo(@Context HttpServletRequest request) {
    logUrlInfo(request);
    return taskService.getTasksInfo();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createTask(@Valid CreateTaskDto task,
                             @Context HttpServletRequest request) {
    logUrlInfo(request);
    return Response
        .status(Response.Status.CREATED)
        .entity(taskService.createTask(task))
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @PATCH
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public TaskDto updateTask(@Valid TaskDto task,
                             @Context HttpServletRequest request) {
    logUrlInfo(request);
    return taskService.updateTask(task);
  }

  @PATCH
  @Path("/toggle")
  @Produces(MediaType.APPLICATION_JSON)
  public List<TaskDto> toggleAll(@Context HttpServletRequest request) {
    logUrlInfo(request);
    return taskService.toggleAll();
  }

  @PATCH
  @Path("/toggle/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public TaskDto toggleById(@PathParam("id") Long id,
                             @Context HttpServletRequest request) {
    logUrlInfo(request);
    return taskService.toggleTaskById(id);
  }

  @DELETE
  @Path("/completed")
  public void clearCompleted(@Context HttpServletRequest request) {
    logUrlInfo(request);
    taskService.deleteCompletedTasks();
  }

  @DELETE
  @Path("/{id}")
  public void deleteTask(@PathParam("id") Long id,
                             @Context HttpServletRequest request) {
    logUrlInfo(request);
    taskService.deleteTaskById(id);
  }

  private void logUrlInfo(HttpServletRequest request) {
    StringBuilder builder = new StringBuilder()
        .append(request.getMethod())
        .append(" ")
        .append(request.getRequestURI());
    Optional.ofNullable(request.getQueryString()).ifPresent(s -> builder.append("?").append(s));
    log.info("{}", builder);
  }

}
