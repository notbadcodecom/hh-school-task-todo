package com.notbadcode.todo.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ClientExceptionMapper implements ExceptionMapper<ClientErrorException> {

  Logger log = LoggerFactory.getLogger(ClientExceptionMapper.class);

  @Context
  private HttpServletRequest request;

  @Override
  public Response toResponse(ClientErrorException exception) {
    log.info("{}: {} [{}]",
        exception.getResponse().getStatusInfo().getReasonPhrase(), exception.getMessage(), request.getRequestURI());
    ErrorDto error = new ErrorDto(exception.getMessage(),
        exception.getResponse().getStatus(),
        exception.getResponse().getStatusInfo().getReasonPhrase(),
        request.getRequestURI());
    return Response
        .status(exception.getResponse().getStatusInfo())
        .entity(error)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

}
