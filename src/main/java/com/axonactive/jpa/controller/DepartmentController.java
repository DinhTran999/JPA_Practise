package com.axonactive.jpa.controller;

import com.axonactive.jpa.controller.request.DepartmentRequest;
import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.service.JWTAuthenticationServices;
import com.axonactive.jpa.service.impl.DepartmentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Api(value = "departments")
public class DepartmentController {

    @Inject
    private DepartmentServiceImpl departmentService;

    @Inject
    JWTAuthenticationServices jwtAuthenticationServices;

    @GET
    @ApiOperation(value = "getAllDepartments", response =Department.class, responseContainer = "List")
    public Response getAllDepartments(@HeaderParam("Authorization") String authorization){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        return Response.ok(departmentService.findAll()).build();
    }


    @GET
    @Path("/{id}")
    @ApiOperation("get department by Id")
    public Response getDepartmentById(@HeaderParam("Authorization") String authorization,@PathParam("id") int id){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        return Response.ok(departmentService.findById(id)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("add department")
    public Response addDepartment(@HeaderParam("Authorization") String authorization,DepartmentRequest departmentRequest){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        return Response.ok(departmentService.saveDepartment(departmentRequest)).build();

    }

    @DELETE
    @Path("/{id}")
    @ApiOperation("delete department")
    public Response deleteDepartment(@HeaderParam("Authorization") String authorization,@PathParam("id") int id){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        departmentService.deleteDepartment(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @ApiOperation("update department")
    public Response updateDepartment(@HeaderParam("Authorization") String authorization,@PathParam("id") int id, DepartmentRequest departmentRequest){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        return Response.ok(departmentService.updateDepartment(id,departmentRequest)).build();
    }



}
