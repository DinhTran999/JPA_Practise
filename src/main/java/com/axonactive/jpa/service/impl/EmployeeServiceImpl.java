package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.service.dto.EmployeeDTO;
import com.axonactive.jpa.service.mapper.EmployeeMapper;
import com.axonactive.jpa.service.persistence.AbstractCRUDBean;
import com.axonactive.jpa.service.persistence.PersistenceService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@RequestScoped
@Transactional
public class EmployeeServiceImpl extends AbstractCRUDBean<Employee> {

    @Inject
    PersistenceService<Employee> persistenceService;

    public static final int YEARS_TO_SUBTRACT = 21;
    @PersistenceContext(unitName = "jpa")
    EntityManager em;

    @Inject
    EmployeeMapper employeeMapper;

    @Inject
    DepartmentServiceImpl departmentService;

    @Override
    protected PersistenceService<Employee> getPersistenceService() {
        return persistenceService;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.EmployeesToEmployeeDtos(findAll());
    }

    public EmployeeDTO getEmployeeById(int employeeId){
        return employeeMapper.EmployeeToEmployeeDto(findById(employeeId));
    }

    public EmployeeDTO addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.EmployeeRequestToEmployee(employeeRequest);
        employee.setDepartment(departmentService.findById(employeeRequest.getDepartmentId()));
        return employeeMapper.EmployeeToEmployeeDto(save(employee));
    }

    public EmployeeDTO updateEmployeeById(int employeeId, EmployeeRequest employeeRequest) {
        Employee employee = findById(employeeId);
        if(Objects.nonNull(employee)){
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setMiddleName(employeeRequest.getMiddleName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setDateOfBirth(employeeRequest.getDateOfBirth());
            employee.setGender(employeeRequest.getGender());
            employee.setSalary(employeeRequest.getSalary());
            employee.setDepartment(departmentService.findById(employeeRequest.getDepartmentId()));
            return employeeMapper.EmployeeToEmployeeDto(update(employee));
        }
        throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Không có Employee với Id: "+employeeId).build());
    }


    public List<EmployeeDTO> getAllEmployeeByDepartment(int departmentId){
        return employeeMapper.EmployeesToEmployeeDtos(createTypeQuery("from Employee e where e.departmentId =:departmentId")
                .setParameter("departmentId", departmentId)
                .getResultList());
    }

}
