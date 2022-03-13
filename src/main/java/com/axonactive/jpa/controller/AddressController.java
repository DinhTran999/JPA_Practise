package com.axonactive.jpa.controller;


import com.axonactive.jpa.enumerate.AddressType;
import com.axonactive.jpa.service.AddressService;
import com.axonactive.jpa.service.dto.AddressDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/employees/{employeeId}/addresses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressController {

    @Inject
    AddressService addressService;


    @GET
    public Response getAllAddressByEmployeeId(@PathParam("employeeId") int employeeId){
        return Response.ok(addressService.getAllAddressByEmployeeId(employeeId)).build();
    }

    @GET
    @Path("/search")
    public Response getAddressesByEmployeeIdAndAddressType(@PathParam("employeeId") int employeeId, @QueryParam("addressType")AddressType addressType){
        return Response.ok(addressService.getAddressesByEmployeeIdAndAddressType(employeeId,addressType)).build();
    }

    @GET
    @Path("/{addressId}")
    public Response getAddressByEmployeeIdAndAddressId(@PathParam("employeeId") int employeeId, @PathParam("addressId") int addressId){
        return Response.ok(addressService.getAddressByEmployeeIdAndAddressId(employeeId,addressId)).build();
    }

    @POST
    public Response addAddress (@PathParam("employeeId") int employeeId, AddressDTO addressDTO){
        return Response.ok(addressService.addAddress(employeeId,addressDTO)).build();
    }

    @DELETE
    @Path("/{addressId}")
    public Response deleteAddressByEmployeeIdAndAddressId(@PathParam("employeeId") int employeeId, @PathParam("addressId") int addressId){
        addressService.deleteAddressByEmployeeIdAndAddressId(employeeId,addressId);
        return Response.ok().build();
    }

    @PUT
    @Path("/{addressId}")
    public Response updateAddressByEmployeeIdAndAddressId(@PathParam("employeeId") int employeeId, @PathParam("addressId") int addressId, AddressDTO addressDTO){
        return Response.ok(addressService.updateAddressByEmployeeIdAndAddressId(employeeId,addressId,addressDTO)).build();
    }


}
