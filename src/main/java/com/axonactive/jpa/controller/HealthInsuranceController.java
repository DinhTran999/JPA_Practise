package com.axonactive.jpa.controller;


import com.axonactive.jpa.service.HealthInsuranceService;
import com.axonactive.jpa.service.dto.HealthInsuranceDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/employees/{employeeId}/health-insurances")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HealthInsuranceController {

    @Inject
    HealthInsuranceService healthInsuranceService;


    @GET
    public Response getHealthInsuranceByEmployeeId(@PathParam("employeeId") int employeeId){
        return Response.ok(healthInsuranceService.getHealthInsuranceByEmployeeId(employeeId)).build();
    }


    @GET
    @Path("/{healthInsuranceId}")
    public Response getHealthInsuranceByEmployeeIdAndHealthInsuranceId(@PathParam("employeeId") int employeeId, @PathParam("healthInsuranceId") int healthInsuranceId){
        return Response.ok(healthInsuranceService.getHealthInsuranceByEmployeeIdAndHealthInsuranceId(employeeId,healthInsuranceId)).build();
    }

    @POST
    public Response addHealthInsurance(@PathParam("employeeId") int employeeId, HealthInsuranceDTO healthInsuranceDTO){
        return Response.ok(healthInsuranceService.addHealthInsurance(employeeId, healthInsuranceDTO)).build();
    }

    @DELETE
    @Path("/{healthInsuranceId}")
    public Response deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId(@PathParam("employeeId") int employeeId, @PathParam("healthInsuranceId") int healthInsuranceId){
        healthInsuranceService.deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId(employeeId, healthInsuranceId);
        return Response.ok().build();
    }


    @PUT
    @Path("/{healthInsuranceId}")
    public Response updateHealthInsurance(@PathParam("employeeId") int employeeId,@PathParam("healthInsuranceId") int healthInsuranceId, HealthInsuranceDTO healthInsuranceDTO){
        return Response.ok(healthInsuranceService.updateHealthInsurance(employeeId,healthInsuranceId, healthInsuranceDTO)).build();
    }




}
