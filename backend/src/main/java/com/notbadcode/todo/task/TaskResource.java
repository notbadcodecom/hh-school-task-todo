package com.notbadcode.todo.task;

import com.notbadcode.todo.task.dto.TaskDto;
import com.notbadcode.todo.task.dto.TasksInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Path("/tasks")
public class TaskResource {
  private final TaskService taskService;
  private final HttpServletRequest request;
  private final Logger log;

  @Autowired
  public TaskResource(TaskServiceImpl taskService,
                      HttpServletRequest request) {
    this.taskService = taskService;
    this.request = request;
    this.log = LoggerFactory.getLogger(TaskResource.class);
  }

  @GET
  @Path("/{id}")
  public Response getTaskById(@PathParam("id") Long id) {
    logUrlInfo();
    TaskDto task = taskService.findTaskById(id);
    return Response
        .status(Response.Status.OK)
        .entity(task)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @GET
  public Response getAllByFilter(@QueryParam("filter") @DefaultValue("all") String filter) {
    logUrlInfo();
    List<TaskDto> tasks = taskService.findAllByFilter(filter);
    return Response
        .status(Response.Status.OK)
        .entity(tasks)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @GET
  @Path("/info")
  public Response getTasksInfo(@QueryParam("filter") @DefaultValue("all") String filter) {
    logUrlInfo();
    TasksInfoDto tasksInfoDto = taskService.getTasksInfo(filter);
    return Response
        .status(Response.Status.OK)
        .entity(tasksInfoDto)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createTask(String json) {
    logUrlInfo();
    TaskDto task = taskService.createTaskFromString(json);
    return Response
        .status(Response.Status.CREATED)
        .entity(task)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @PATCH
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateTask(String json) {
    logUrlInfo();
    TaskDto task = taskService.updateTaskFromString(json);
    return Response
        .status(Response.Status.OK)
        .entity(task)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @PATCH
  @Path("/toggle")
  public Response toggleAll() {
    logUrlInfo();
    List<TaskDto> tasks = taskService.toggleAll();
    return Response
        .status(Response.Status.OK)
        .entity(tasks)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @PATCH
  @Path("/toggle/{id}")
  public Response toggleById(@PathParam("id") Long id) {
    logUrlInfo();
    TaskDto task = taskService.toggleTaskById(id);
    return Response
        .status(Response.Status.OK)
        .entity(task)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  @DELETE
  @Path("/completed")
  public Response clearCompleted() {
    logUrlInfo();
    taskService.deleteCompletedTasks();
    return Response
        .status(Response.Status.NO_CONTENT)
        .build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteTask(@PathParam("id") Long id) {
    logUrlInfo();
    taskService.deleteTaskById(id);
    return Response
        .status(Response.Status.NO_CONTENT)
        .build();
  }

  private void logUrlInfo() {
    StringBuilder builder = new StringBuilder()
        .append(request.getMethod())
        .append(" ")
        .append(request.getRequestURI());
    Optional.ofNullable(request.getQueryString()).ifPresent(s -> builder.append("?").append(s));
    log.info("{}", builder);
  }
}
