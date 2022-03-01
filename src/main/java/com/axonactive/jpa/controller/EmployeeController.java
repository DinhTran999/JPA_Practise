package com.axonactive.jpa.controller;


import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.service.EmployeeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/departments/{departmentId}/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeController {

    @Inject
    private EmployeeService employeeService;

    @GET
    public Response getAllEmployeeByDepartment(@PathParam("departmentId") int departmentId){
        return Response.ok(employeeService.getAllEmployeeByDepartment(departmentId)).build();
    }

    @GET
    @Path("/{EmployeeId}")
    public Response getEmployeeById(@PathParam("departmentId") int departmentId, @PathParam("EmployeeId") int EmployeeId){
        return Response.ok(employeeService.getEmployeeByDeptIdAndEmployeeId(departmentId,EmployeeId)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEmployee(@PathParam("departmentId") int departmentId, EmployeeRequest EmployeeRequest){
        return Response.ok(employeeService.addEmployee(departmentId, EmployeeRequest)).build();

    }

    @DELETE
    @Path("/{EmployeeId}")
    public Response deleteEmployee(@PathParam("departmentId") int departmentId, @PathParam("EmployeeId") int EmployeeId){
        employeeService.deleteEmployee(departmentId,EmployeeId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{EmployeeId}")
    public Response updateEmployee(@PathParam("departmentId") int departmentId,@PathParam("EmployeeId") int EmployeeId, EmployeeRequest EmployeeRequest){
        return Response.ok(employeeService.updateEmployee(departmentId,EmployeeId,EmployeeRequest)).build();
    }

}
