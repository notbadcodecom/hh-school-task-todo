package com.notbadcode.todo.exception;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClientExceptionMapper implements ExceptionMapper<ClientErrorException> {

  @Override
  public Response toResponse(ClientErrorException exception) {

    ErrorDto error = new ErrorDto(exception.getMessage(),
        exception.getResponse().getStatus(),
        exception.getResponse().getStatusInfo().getReasonPhrase());
    return Response
        .status(exception.getResponse().getStatusInfo())
        .entity(error)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

}
