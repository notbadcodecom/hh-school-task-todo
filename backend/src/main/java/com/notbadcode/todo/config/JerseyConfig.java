package com.notbadcode.todo.config;

import com.notbadcode.todo.exception.ClientExceptionMapper;
import com.notbadcode.todo.task.TaskResource;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    register(TaskResource.class);
    register(ClientExceptionMapper.class);
  }

}
