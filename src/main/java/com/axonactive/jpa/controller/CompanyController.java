package com.axonactive.jpa.controller;

import com.axonactive.jpa.service.EmployeeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/statistic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyController {
    @Inject
    EmployeeService employeeService;

    @GET
    public Response getAllEmployeeGroupByDepartment(){
        return Response.ok(employeeService.getAllEmployeeGroupByDepartment()).build();
    }

    @GET
    @Path("/employeebybirthmonth/{month}")
    public Response getEmployeeByBirthMonth(@PathParam("month") int month){
        return Response.ok(employeeService.getEmployeeByBirthMonth(month)).build();
    }


}
