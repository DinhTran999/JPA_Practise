package com.axonactive.jpa.controller;

import com.axonactive.jpa.controller.request.UserRequest;
import com.axonactive.jpa.entities.Token;
import com.axonactive.jpa.entities.User;
import com.axonactive.jpa.service.JWTAuthenticationServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "AuthController")
public class AuthController {

    @Inject
    JWTAuthenticationServices jwtAuthenticationServices;

    @POST
    @ApiOperation("get Authorized Token")
    public Response getAuthorizedToken(UserRequest userRequest){
        Token token = jwtAuthenticationServices.createToken(userRequest);
        return Response.ok(token).build();
    }


}
