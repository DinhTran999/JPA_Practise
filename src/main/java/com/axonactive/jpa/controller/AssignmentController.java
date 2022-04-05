package com.axonactive.jpa.controller;


import com.axonactive.jpa.controller.request.AssignmentRequest;
import com.axonactive.jpa.service.AssignmentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/assignments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AssignmentController {

    @Inject
    private AssignmentService assignmentService;

    @GET
    public Response getAssignments(){
        return Response.ok(assignmentService.getAssignments()).build();
    }


    @GET
    @Path("/{id}")
    public Response getAssignmentById(@PathParam("id") int id){
        return Response.ok(assignmentService.getAssignmentById(id)).build();
    }


    @POST
    public Response addAssignment(@Valid AssignmentRequest assignmentRequest){
        return Response.ok(assignmentService.addAssignment(assignmentRequest)).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteAssignment(@PathParam("id") int id){
        assignmentService.deleteAssignment(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAssignment(@PathParam("id") int assignmentId,@Valid AssignmentRequest assignmentRequest){
        return Response.ok(assignmentService.updateAssignment(assignmentId,assignmentRequest)).build();
    }



}

