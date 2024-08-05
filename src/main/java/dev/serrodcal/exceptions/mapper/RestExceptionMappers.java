package dev.serrodcal.exceptions.mapper;

import dev.serrodcal.resources.dtos.exception.ExceptionResponse;
import org.hibernate.exception.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class RestExceptionMappers {

    @ServerExceptionMapper
    public Response mapException(ConstraintViolationException ex) {
        return Response.status(Response.Status.CONFLICT).entity(new ExceptionResponse(ex.getMessage())).build();
    }

}
