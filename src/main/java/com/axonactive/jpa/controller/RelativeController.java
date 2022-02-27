package com.axonactive.jpa.controller;

import com.axonactive.jpa.service.EmployeeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/employees/{employeeID}/relatives")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RelativeController {

    @Inject
    EmployeeService employeeService;

    @GET
    public Response getAllRelativesByEmployee(@PathParam("employeeID") int employeeID){
        return Response.ok(employeeService.getAllRelativesByEmployee(employeeID)).build();
    }

}
