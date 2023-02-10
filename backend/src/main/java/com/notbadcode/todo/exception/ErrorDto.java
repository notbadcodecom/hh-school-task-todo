package com.notbadcode.todo.exception;

import java.time.LocalDateTime;

public class ErrorDto {

  private final String error;

  private final Integer code;

  private final String message;

  private final LocalDateTime time;

  protected ErrorDto(String message, Integer code, String error) {
    this.message = message;
    this.code = code;
    this.error = error;
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

  public String getError() {
    return error;
  }

}
