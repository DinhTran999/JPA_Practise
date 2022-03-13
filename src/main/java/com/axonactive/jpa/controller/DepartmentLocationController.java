package com.axonactive.jpa.controller;

import com.axonactive.jpa.controller.request.DepartmentLocationRequest;
import com.axonactive.jpa.service.DepartmentLocationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/departments/{departmentId}/locations")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentLocationController {
    @Inject
    private DepartmentLocationService departmentLocationService;

    @GET
    public Response getAllLocationByDepartment(@PathParam("departmentId") int departmentId){
        return Response.ok(departmentLocationService.getAllLocationByDepartment(departmentId)).build();
    }

    @GET
    @Path("/{locationId}")
    public Response getDepartmentLocationById(@PathParam("departmentId") int departmentId, @PathParam("locationId") int locationId){
        return Response.ok(departmentLocationService.getDepartmentLocationById(departmentId,locationId)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDepartmentLocation(@PathParam("departmentId") int departmentId, DepartmentLocationRequest departmentLocationRequest){
        return Response.ok(departmentLocationService.addDepartmentLocation(departmentId, departmentLocationRequest)).build();

    }

    @DELETE
    @Path("/{locationId}")
    public Response deleteDepartmentLocation(@PathParam("departmentId") int departmentId, @PathParam("locationId") int locationId){
        departmentLocationService.deleteDepartmentLocation(departmentId,locationId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{locationId}")
    public Response updateDepartmentLocation(@PathParam("departmentId") int departmentId,@PathParam("locationId") int locationId, DepartmentLocationRequest departmentLocationRequest){
        return Response.ok(departmentLocationService.updateDepartmentLocation(departmentId,locationId,departmentLocationRequest)).build();
    }
    
    
}
