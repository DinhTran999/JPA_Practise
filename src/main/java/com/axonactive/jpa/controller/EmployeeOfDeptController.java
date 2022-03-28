package com.axonactive.jpa.controller;


import com.axonactive.jpa.controller.request.EmployeeOfDepartmentRequest;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.impl.EmployeeServiceImpl;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/departments/{departmentId}/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeOfDeptController {

    @Inject
    private EmployeeServiceImpl employeeService;

    @GET
    public Response getAllEmployeeByDepartment(@PathParam("departmentId") int departmentId){
        return Response.ok(employeeService.getAllEmployeeByDepartment(departmentId)).build();
    }

    @GET
    @Path("/{EmployeeId}")
    public Response getEmployeeById(@PathParam("departmentId") int departmentId, @PathParam("EmployeeId") int employeeId){
//        return Response.ok(employeeService.getEmployeeByDeptIdAndEmployeeId(departmentId,employeeId)).build();
        return Response.ok().build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEmployee(@PathParam("departmentId") int departmentId,@Valid EmployeeOfDepartmentRequest employeeRequest){
//        return Response.ok(employeeService.addEmployeeByDepartmentId(departmentId, employeeRequest)).build();
        return Response.ok().build();

    }

    @DELETE
    @Path("/{EmployeeId}")
    public Response deleteEmployee(@PathParam("departmentId") int departmentId, @PathParam("EmployeeId") int employeeId){
//        employeeService.deleteEmployeeByDepartmentIdAndEmployeeId(departmentId,employeeId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{EmployeeId}")
    public Response updateEmployee(@PathParam("departmentId") int departmentId,@PathParam("EmployeeId") int employeeId, EmployeeOfDepartmentRequest employeeRequest){
//        return Response.ok(employeeService.updateEmployeeByDepartmentIdAndEmployeeId(departmentId,employeeId,employeeRequest)).build();
        return Response.ok().build();
    }

}
