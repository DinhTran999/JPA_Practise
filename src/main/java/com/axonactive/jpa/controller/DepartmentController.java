package com.axonactive.jpa.controller;

import com.axonactive.jpa.controller.request.DepartmentRequest;
import com.axonactive.jpa.service.DepartmentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentController {

    @Inject
    private DepartmentService departmentService;

    @GET
    public Response getAllDepartments(){
        return Response.ok(departmentService.getAllDepartment()).build();
    }


    @GET
    @Path("/{id}")
    public Response getDepartmentById(@PathParam("id") int id){
        return Response.ok(departmentService.getDepartmentById(id)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDepartment(DepartmentRequest departmentRequest){
        return Response.ok(departmentService.addDepartment(departmentRequest)).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteDepartment(@PathParam("id") int id){
        departmentService.deleteDepartment(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateDepartment(@PathParam("id") int id, DepartmentRequest departmentRequest){
        return Response.ok(departmentService.updateDepartment(id,departmentRequest)).build();
    }



}
