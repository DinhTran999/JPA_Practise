package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.service.DepartmentService;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.dto.EmployeeDTO;
import com.axonactive.jpa.service.mapper.EmployeeMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@RequestScoped
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @PersistenceContext(unitName = "jpa")
    EntityManager em;

    @Inject
    EmployeeMapper employeeMapper;

    @Inject
    DepartmentService departmentService;

    @Override
    public List<EmployeeDTO> getAllEmployeeByDepartment(int departmentId) {
        TypedQuery<Employee> namedQuery = em.createNamedQuery(Employee.GET_ALL_BY_DEPT_ID_AND_EMPLOYEE_ID, Employee.class);
        namedQuery.setParameter("departmentId", departmentId);
        List<Employee> employeeList = namedQuery.getResultList();
        return employeeMapper.EmployeesToEmployeeDtos(employeeList);
    }


    private List<Employee> getEmployeeList() {
        return em.createNamedQuery(Employee.GET_ALL, Employee.class).getResultList();
    }

    @Override
    public EmployeeDTO getEmployeeById(int employeeId){
        return employeeMapper.EmployeeToEmployeeDto(getEmployeeByIdFromDataBase(employeeId));

    }

    @Override
    public Employee getEmployeeByIdFromDataBase(int employeeId){
        return em.find(Employee.class,employeeId);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.EmployeesToEmployeeDtos(getEmployeeList());
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.EmployeeRequestToEmployee(employeeRequest);
        employee.setDepartment(departmentService.getDepartmentById(employeeRequest.getDepartmentId()));
        em.persist(employee);
        return employeeMapper.EmployeeToEmployeeDto(employee);
    }

    @Override
    public void deleteEmployeeById(int employeeId) {
        Employee employee = getEmployeeByIdFromDataBase(employeeId);
        if(Objects.nonNull(employee)) em.remove(employee);
    }

    @Override
    public EmployeeDTO updateEmployeeById(int employeeId, EmployeeRequest employeeRequest) {
        Employee employee = getEmployeeByIdFromDataBase(employeeId);
        if(Objects.nonNull(employee)){
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setMiddleName(employeeRequest.getMiddleName());
            employee.setLastName(employeeRequest.getLastName());
            employee.setDateOfBirth(employeeRequest.getDateOfBirth());
            employee.setGender(employeeRequest.getGender());
            employee.setSalary(employeeRequest.getSalary());
            employee.setDepartment(departmentService.getDepartmentById(employeeRequest.getDepartmentId()));
            return employeeMapper.EmployeeToEmployeeDto(em.merge(employee));
        }
        throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Không có Employee với Id: "+employeeId).build());
    }


}
