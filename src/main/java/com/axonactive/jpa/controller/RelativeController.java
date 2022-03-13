package com.axonactive.jpa.controller;

import com.axonactive.jpa.controller.request.RelativeRequest;
import com.axonactive.jpa.service.RelativeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/employees/{employeeId}/relatives")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RelativeController {

    @Inject
    RelativeService relativeService;

    @GET
    public Response getAllRelativesByEmployeeId(@PathParam("employeeId") int employeeId){
        return Response.ok(relativeService.getAllRelativeByEmployeeId(employeeId)).build();
    }

    @GET
    @Path("/{relativeId}")
    public Response getRelativeByEmployeeIdAndRelativeId(@PathParam("employeeId") int employeeId,
                                                         @PathParam("relativeId") int relativeId){
        return Response.ok(relativeService.getRelativeByEmployeeIdAndRelativeId(employeeId,relativeId)).build();
    }

    @POST
    public Response addRelativeByEmployeeId(@PathParam("employeeId") int employeeId, RelativeRequest relativeRequest){
        return Response.ok(relativeService.addRelativeByEmployeeId(employeeId,relativeRequest)).build();
    }

    @DELETE
    @Path("/{relativeId}")
    public Response deleteRelativeByEmployeeIdAndRelativeId(@PathParam("employeeId") int employeeId,
                                                            @PathParam("relativeId") int relativeId){
        relativeService.deleteRelativeByEmployeeIdAndRelativeId(employeeId,relativeId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{relativeId}")
    public Response updateRelativeByEmployeeIdAndRelativeId(@PathParam("employeeId") int employeeId,
                                                            @PathParam("relativeId") int relativeId, RelativeRequest relativeRequest){
        return Response.ok(relativeService.updateRelativeByEmployeeIdAndRelativeId(employeeId,relativeId,relativeRequest)).build();
    }

}
