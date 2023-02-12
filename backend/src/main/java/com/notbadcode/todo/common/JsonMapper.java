package com.notbadcode.todo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper<T> {

  public final ObjectMapper objectMapper;

  @Autowired
  public JsonMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public T fromJson(String json, Class<T> type) {
    T entity;
    try {
      entity = objectMapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      throw new BadRequestException("invalid json in request");
    }
    return entity;
  }
}
