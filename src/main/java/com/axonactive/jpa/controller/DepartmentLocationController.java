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
    private DepartmentLocationService DepartmentLocationService;

    @GET
    public Response getAllLocationByDepartment(@PathParam("departmentId") int departmentId){
        return Response.ok(DepartmentLocationService.getAllLocationByDepartment(departmentId)).build();
    }

    @GET
    @Path("/{locationId}")
    public Response getDepartmentLocationById(@PathParam("departmentId") int departmentId, @PathParam("locationId") int locationId){
        return Response.ok(DepartmentLocationService.getDepartmentLocationById(departmentId,locationId)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDepartmentLocation(@PathParam("departmentId") int departmentId, DepartmentLocationRequest DepartmentLocationRequest){
        return Response.ok(DepartmentLocationService.addDepartmentLocation(departmentId, DepartmentLocationRequest)).build();

    }

    @DELETE
    @Path("/{locationId}")
    public Response deleteDepartmentLocation(@PathParam("departmentId") int departmentId, @PathParam("locationId") int locationId){
        DepartmentLocationService.deleteDepartmentLocation(departmentId,locationId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{locationId}")
    public Response updateDepartmentLocation(@PathParam("departmentId") int departmentId,@PathParam("locationId") int locationId, DepartmentLocationRequest DepartmentLocationRequest){
        return Response.ok(DepartmentLocationService.updateDepartmentLocation(departmentId,locationId,DepartmentLocationRequest)).build();
    }
    
    
}
