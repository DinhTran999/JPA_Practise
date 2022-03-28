package com.axonactive.jpa.controller;

import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.impl.EmployeeServiceImpl;
import com.axonactive.jpa.service.impl.RelativeServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/statistic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyController {
    @Inject
    EmployeeServiceImpl employeeService;

    @Inject
    RelativeServiceImpl relativeService;

    @GET
    public Response getAllEmployeeGroupByDepartment(){
//        return Response.ok(employeeService.getAllEmployeeGroupByDepartment()).build();
        return Response.ok().build();
    }

    @GET
    @Path("/employeebybirthmonth/{month}")
    public Response getEmployeeByBirthMonth(@PathParam("month") int month){
//        return Response.ok(employeeService.getEmployeeByBirthMonth(month)).build();
        return Response.ok().build();
    }


    @GET
    @Path("relativeofemployee")
    public Response getRelativeOfEmployee() {
        return Response.ok(relativeService.getRelativeOfEmployee()).build();
    }

    @GET
    @Path("employee-emergency")
    public Response getEmployeeEmergencyRelative(){
        return Response.ok(relativeService.getEmployeeEmergencyRelative()).build();
    }
}
