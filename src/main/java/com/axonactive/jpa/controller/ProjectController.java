package com.axonactive.jpa.controller;


import com.axonactive.jpa.controller.request.ProjectRequest;
import com.axonactive.jpa.service.ProjectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectController {

    @Inject
    private ProjectService projectService;

    @GET
    public Response getAllProject(){
        return Response.ok(projectService.getAllProject()).build();
    }

    @GET
    @Path("/{projectId}")
    public Response getProjectById(@PathParam("projectId") int projectId){
        return Response.ok(projectService.getProjectById(projectId)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProject(ProjectRequest projectRequest){
        return Response.ok(projectService.addProject(projectRequest)).build();
    }

    @DELETE
    @Path("/{projectId}")
    public Response deleteProject(@PathParam("projectId") int projectId){
        projectService.deleteProjectById(projectId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{projectId}")
    public Response updateProject(@PathParam("projectId") int projectId, ProjectRequest projectRequest){
        return Response.ok(projectService.updateProject(projectId,projectRequest)).build();
    }

}
