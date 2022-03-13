package com.axonactive.jpa.exeption;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NoSuchDepartmentException extends WebApplicationException {
//    private String message = "Không tồn tại Department này";

    public NoSuchDepartmentException(){
        super(createResponse());
    }

    private static Response createResponse(){
       return Response.status(Response.Status.BAD_REQUEST).entity("Không tồn tại Department này").build();
    }



}
