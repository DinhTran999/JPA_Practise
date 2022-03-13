package com.axonactive.jpa.controller;

import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.service.EmployeeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeController {
    @Inject
    EmployeeService employeeService;

    @GET
    public Response getAllEmployees(){
        return Response.ok(employeeService.getAllEmployees()).build();
    }

    @GET
    @Path("/{employeeId}")
    public Response getEmployeeById(@PathParam("employeeId") int employeeId){
        return  Response.ok(employeeService.getEmployeeById(employeeId)).build();
    }


    @POST
    public Response addEmployee(EmployeeRequest employeeRequest){
        return Response.ok(employeeService.addEmployee(employeeRequest)).build();
    }

    @DELETE
    @Path("/{employeeId}")
    public Response deleteEmployeeById(@PathParam("employeeId") int employeeId){
        employeeService.deleteEmployeeById(employeeId);
        return Response.ok().build();

    }

    @PUT
    @Path("/{employeeId}")
    public Response updateEmployeeById(@PathParam("employeeId") int employeeId, EmployeeRequest employeeRequest){
        return Response.ok(employeeService.updateEmployeeById(employeeId,employeeRequest)).build();
    }
}
