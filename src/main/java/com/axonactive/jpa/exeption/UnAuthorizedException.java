package com.axonactive.jpa.exeption;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class UnAuthorizedException extends WebApplicationException {
    public UnAuthorizedException(){
        super(createResponse());
    }

    private static Response createResponse(){
       return Response.status(Response.Status.FORBIDDEN).entity("User not in system").build();
    }



}
