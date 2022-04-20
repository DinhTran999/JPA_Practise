package com.axonactive.jpa.exeption;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DepartmentException extends WebApplicationException {


    public DepartmentException(Response.Status status,String message) {
        super(Response.status(status).entity(message).build());
    }
}
