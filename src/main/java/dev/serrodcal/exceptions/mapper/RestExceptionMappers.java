package dev.serrodcal.exceptions.mapper;

import dev.serrodcal.resources.dtos.exception.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.NoSuchElementException;

public class RestExceptionMappers {

    @ServerExceptionMapper
    public Response badRequest(jakarta.validation.ConstraintViolationException ex) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ExceptionResponse(ex.getMessage())).build();
    }

    @ServerExceptionMapper
    public Response conflict(org.hibernate.exception.ConstraintViolationException ex) {
        return Response.status(Response.Status.CONFLICT).entity(new ExceptionResponse(ex.getMessage())).build();
    }

    @ServerExceptionMapper
    public Response noSuchelement(NoSuchElementException ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionResponse(ex.getMessage())).build();
    }

}
