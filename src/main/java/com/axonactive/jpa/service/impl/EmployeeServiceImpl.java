package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.entities.Assignment;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.service.AssignmentService;
import com.axonactive.jpa.service.dto.employee.EmployeeDTO;
import com.axonactive.jpa.service.dto.employee.EmployeeProjectDTO;
import com.axonactive.jpa.service.dto.project.ProjectDTO;
import com.axonactive.jpa.service.mapper.EmployeeMapper;
import com.axonactive.jpa.service.mapper.ProjectMapper;
import com.axonactive.jpa.service.persistence.AbstractCRUDBean;
import com.axonactive.jpa.service.persistence.PersistenceService;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@RequestScoped
@Transactional
@Log4j2
public class EmployeeServiceImpl extends AbstractCRUDBean<Employee> {

    @Inject
    PersistenceService<Employee> persistenceService;

    public static final int YEARS_TO_SUBTRACT = 21;

    @Inject
    EmployeeMapper employeeMapper;

    @Inject
    ProjectMapper projectMapper;

    @Inject
    DepartmentServiceImpl departmentService;

    @Inject
    AssignmentService assignmentService;

    @Override
    protected PersistenceService<Employee> getPersistenceService() {
        return persistenceService;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.EmployeesToEmployeeDtos(findAll());
    }

    public EmployeeDTO getEmployeeById(int employeeId) {
        return employeeMapper.EmployeeToEmployeeDto(findById(employeeId));
    }

    public EmployeeDTO addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.EmployeeRequestToEmployee(employeeRequest);
        employee.setDepartment(departmentService.findById(employeeRequest.getDepartmentId()));
        return employeeMapper.EmployeeToEmployeeDto(save(employee));
    }

    public EmployeeDTO updateEmployeeById(int employeeId, EmployeeRequest employeeRequest) {
        Employee employee = findById(employeeId);
        if (Objects.nonNull(employee)) {
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setMiddleName(employeeRequest.getMiddleName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setDateOfBirth(employeeRequest.getDateOfBirth());
            employee.setGender(employeeRequest.getGender());
            employee.setSalary(employeeRequest.getSalary());
            employee.setDepartment(departmentService.findById(employeeRequest.getDepartmentId()));
            return employeeMapper.EmployeeToEmployeeDto(update(employee));
        }
        throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Không có Employee với Id: " + employeeId).build());
    }


    public List<EmployeeDTO> getAllEmployeeByDepartment(int departmentId) {
        return employeeMapper.EmployeesToEmployeeDtos(createTypeQuery("from Employee e where e.departmentId =:departmentId")
                .setParameter("departmentId", departmentId)
                .getResultList());
    }

    /**
     * get employees has not being assigned in any project
     */

    public List<EmployeeDTO> getAllEmployeesNotAssigned() {
        List<Integer> employeeInProject = assignmentService.getAssignmentsFromDatabase()
                .stream()
                .map(a -> a.getEmployee().getId())
                .distinct()
                .collect(Collectors.toList());
        return getAllEmployees()
                .stream()
                .filter(e -> !employeeInProject.contains(e.getId()))
                .collect(Collectors.toList());
    }

    /**
     * List of employee work in project which has been managed by another department
     * */
    public List<EmployeeProjectDTO> getEmployeesWorkInOtherDepartment() {
        return assignmentService.getAssignmentsFromDatabase()
                .stream()
                .collect(Collectors.groupingBy(Assignment::getEmployee))
                .entrySet()
                .stream()
                .filter(employeeListEntry->employeeListEntry.getValue()
                        .stream()
                        .map(a->a.getProject().getDepartment().getId())
                        .collect(Collectors.toList())
                        .stream()
                        .anyMatch(departmentId-> !Objects.equals(departmentId, employeeListEntry.getKey().getDepartment().getId())))
                .map(entry -> {
                    EmployeeDTO employeeDTO = employeeMapper.EmployeeToEmployeeDto(entry.getKey());
                    List<ProjectDTO> projectDTOS = projectMapper.ProjectsToProjectDtos(entry.getValue()
                            .stream()
                            .map(Assignment::getProject)
                            .collect(Collectors.toList()));
                    return new EmployeeProjectDTO(employeeDTO, projectDTOS);
                }).collect(Collectors.toList());
    }
}
