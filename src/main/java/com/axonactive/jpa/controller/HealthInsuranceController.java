package com.axonactive.jpa.controller;


import com.axonactive.jpa.service.HealthInsuranceService;
import com.axonactive.jpa.service.JWTAuthenticationServices;
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

    @Inject
    JWTAuthenticationServices jwtAuthenticationServices;


    @GET
    public Response getHealthInsuranceByEmployeeId(@HeaderParam("Authorization") String authorization,@PathParam("employeeId") int employeeId){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        return Response.ok(healthInsuranceService.getHealthInsuranceByEmployeeId(employeeId)).build();
    }


    @GET
    @Path("/{healthInsuranceId}")
    public Response getHealthInsuranceByEmployeeIdAndHealthInsuranceId(@HeaderParam("Authorization") String authorization, @PathParam("employeeId") int employeeId, @PathParam("healthInsuranceId") int healthInsuranceId){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        return Response.ok(healthInsuranceService.getHealthInsuranceByEmployeeIdAndHealthInsuranceId(employeeId,healthInsuranceId)).build();
    }

    @POST
    public Response addHealthInsurance(@HeaderParam("Authorization") String authorization, @PathParam("employeeId") int employeeId, HealthInsuranceDTO healthInsuranceDTO){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        return Response.ok(healthInsuranceService.addHealthInsurance(employeeId, healthInsuranceDTO)).build();
    }

    @DELETE
    @Path("/{healthInsuranceId}")
    public Response deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId(@HeaderParam("Authorization") String authorization, @PathParam("employeeId") int employeeId, @PathParam("healthInsuranceId") int healthInsuranceId){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        healthInsuranceService.deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId(employeeId, healthInsuranceId);
        return Response.ok().build();
    }


    @PUT
    @Path("/{healthInsuranceId}")
    public Response updateHealthInsurance(@HeaderParam("Authorization") String authorization, @PathParam("employeeId") int employeeId,@PathParam("healthInsuranceId") int healthInsuranceId, HealthInsuranceDTO healthInsuranceDTO){
        jwtAuthenticationServices.checkAuthorizedToken(authorization);
        return Response.ok(healthInsuranceService.updateHealthInsurance(employeeId,healthInsuranceId, healthInsuranceDTO)).build();
    }




}
