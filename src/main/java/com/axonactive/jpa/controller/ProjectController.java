package com.axonactive.jpa.controller;


import com.axonactive.jpa.controller.request.ProjectRequest;
import com.axonactive.jpa.service.ProjectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/departments/{departmentId}/projects")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectController {

    @Inject
    private ProjectService projectService;

    @GET
    public Response getAllProjectByDepartment(@PathParam("departmentId") int departmentId){
        return Response.ok(projectService.getAllProjectByDepartment(departmentId)).build();
    }

    @GET
    @Path("/{projectId}")
    public Response getProjectById(@PathParam("departmentId") int departmentId, @PathParam("projectId") int projectId){
        return Response.ok(projectService.getProjectById(departmentId,projectId)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProject(@PathParam("departmentId") int departmentId, ProjectRequest projectRequest){
        return Response.ok(projectService.addProject(departmentId, projectRequest)).build();

    }

    @DELETE
    @Path("/{projectId}")
    public Response deleteProject(@PathParam("departmentId") int departmentId, @PathParam("projectId") int projectId){
        projectService.deleteProject(departmentId,projectId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{projectId}")
    public Response updateProject(@PathParam("departmentId") int departmentId,@PathParam("projectId") int projectId, ProjectRequest projectRequest){
        return Response.ok(projectService.updateProject(departmentId,projectId,projectRequest)).build();
    }

}
