package com.axonactive.jpa.controller;

import com.axonactive.jpa.entities.Token;
import com.axonactive.jpa.entities.User;
import com.axonactive.jpa.service.JWTAuthenticationServices;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    JWTAuthenticationServices jwtAuthenticationServices;

    @POST
    public Response getAuthenticationToken(User user){
        Token token = jwtAuthenticationServices.createToken(user);
        return Response.ok(token).build();
    }


}
