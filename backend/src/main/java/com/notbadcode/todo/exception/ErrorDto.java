package com.notbadcode.todo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {

  private final String message;

  private final Integer code;

  private final String reason;

  private final LocalDateTime time;

  private final String uri;

  @JsonProperty("fields_messages")
  private final Map<String, String> fieldsMessages;

  protected ErrorDto(String message, Integer code, String reason, String uri) {
    this.message = message;
    this.code = code;
    this.reason = reason;
    this.uri = uri;
    this.fieldsMessages = null;
    time = LocalDateTime.now();
  }

  protected ErrorDto(Integer code, String reason, String uri, Map<String, String> fields) {
    this.message = null;
    this.code = code;
    this.reason = reason;
    this.uri = uri;
    this.fieldsMessages = fields;
    time = LocalDateTime.now();
  }

  public String getMessage() {
    return message;
  }

  public Integer getCode() {
    return code;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public String getReason() {
    return reason;
  }

  public String getUri() {
    return uri;
  }

  public Map<String, String> getFieldsMessages() {
    return fieldsMessages;
  }

}
