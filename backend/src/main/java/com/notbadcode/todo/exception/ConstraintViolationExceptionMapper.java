package com.notbadcode.todo.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  Logger log = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

  @Context
  private HttpServletRequest request;

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    logErrorInfo(exception);
    Map<String, String> fields = exception.getConstraintViolations().stream()
        .collect(Collectors.toMap(this::getConstraintField, ConstraintViolation::getMessage));
    ErrorDto error = new ErrorDto(Response.Status.BAD_REQUEST.getStatusCode(),
        Response.Status.BAD_REQUEST.getReasonPhrase(),
        request.getRequestURI(),
        fields);
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(error)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  private String getConstraintField(ConstraintViolation constraintViolation) {
    String field = "";
    for (Path.Node node : constraintViolation.getPropertyPath()) {
      field = String.valueOf(node);
    }
    return field;
  }

  private void logErrorInfo(ConstraintViolationException exception) {
    exception.getConstraintViolations().forEach(constraintViolation -> log.info("{}: {} [{}]",
            Response.Status.BAD_REQUEST, constraintViolation.getMessage(), request.getRequestURI()));
  }

}
