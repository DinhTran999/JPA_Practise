package com.axonactive.jpa.exeption;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class NoSuchHealthInsuranceExceptionMapper implements ExceptionMapper<NoSuchHealthInsuranceException> {
    @Override
    public Response toResponse(NoSuchHealthInsuranceException e) {
        return Response.status(BAD_REQUEST).entity("Health Insurance Card not found").build();
    }
}
