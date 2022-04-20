package com.axonactive.jpa.controller;

import com.axonactive.jpa.service.ProjectService;
import com.axonactive.jpa.service.impl.DepartmentServiceImpl;
import com.axonactive.jpa.service.impl.EmployeeServiceImpl;
import com.axonactive.jpa.service.impl.RelativeServiceImpl;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/statistic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class CompanyController {
    @Inject
    EmployeeServiceImpl employeeService;

    @Inject
    RelativeServiceImpl relativeService;

    @Inject
    ProjectService projectService;

    @Inject
    DepartmentServiceImpl departmentService;


    @GET
    public Response getAllEmployeeGroupByDepartment(){
//        return Response.ok(employeeService.getAllEmployeeGroupByDepartment()).build();
        return Response.ok().build();
    }

    @GET
    @Path("/employeebybirthmonth/{month}")
    public Response getEmployeeByBirthMonth(@PathParam("month") int month){
//        return Response.ok(employeeService.getEmployeeByBirthMonth(month)).build();
        return Response.ok().build();
    }


    @GET
    @Path("relativeofemployee")
    public Response getRelativeOfEmployee() {
        return Response.ok(relativeService.getRelativeOfEmployee()).build();
    }

    @GET
    @Path("employee-emergency")
    public Response getEmployeeEmergencyRelative(){
        return Response.ok(relativeService.getEmployeeEmergencyRelative()).build();
    }

    @GET
    @Path("project-with-managed-department")
    public Response getProjectWithManagedDepartment(){
        return Response.ok(projectService.getProjectWithDeptName()).build();
    }

    @GET
    @Path("department-with-projects")
    public Response getDepartmentWithProjects(){
        return Response.ok(departmentService.getAllDepartmentWithProjects()).build();
    }

    @GET
    @Path("project-with-effort/{area}")
    public Response getProjectsWithEffort(@PathParam("area") String area){
        return Response.ok(projectService.getAllProjectWithEffort(area)).build();
    }

    @GET
    @Path("project-with-hours-cost/{area}")
    public Response getProjectsWithCost(@PathParam("area") String area){
        return Response.ok(projectService.getAllProjectWithHoursAndCost(area)).build();
    }

    @GET
    @Path("employee-not-assigned")
    public Response getEmployeesNotAssigned(){
        log.info("get all employee not in any project");
        return Response.ok(employeeService.getAllEmployeesNotAssigned()).build();
    }

    @GET
    @Path("employee-work-in-project-of-other-department")
    public Response getEmployeesWorkInOtherDepartment(){
        return Response.ok(employeeService.getEmployeesWorkInOtherDepartment()).build();
    }

}
