package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.DepartmentRequest;
import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.exeption.NoSuchDepartmentException;
import com.axonactive.jpa.service.DepartmentService;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.dto.EmployeeDTO;

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
public class DepartmentServiceImpl implements DepartmentService {

    @PersistenceContext(unitName = "jpa")
    private EntityManager em;

    @Inject
    private EmployeeService employeeService;

    @Override
    public Department getDepartmentById(int id) {
        return em.find(Department.class,id);
    }

    @Override
    public List<Department> getAllDepartment() {
        return em.createNamedQuery(Department.GET_ALL,Department.class).getResultList();
    }

    @Override
    public Department addDepartment(DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setStartDate(departmentRequest.getStartDate());
        em.persist(department);
        return department;
    }

    @Override
    public void deleteDepartment(int id) {
        Department department = getDepartmentById(id);
        if(Objects.isNull(department)){
            throw new NoSuchDepartmentException();
//            throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Không tồn tại Department này").build());
        }
        if(isDepartmentHasEmployees(id)){
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Department có employee không thể xóa").build());
        }
        em.remove(department);
    }

    private boolean isDepartmentHasEmployees(int departmentId){
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployeeByDepartment(departmentId);
        return !employeeDTOList.isEmpty();
    }

    @Override
    public Department updateDepartment(int id, DepartmentRequest departmentRequest) {
        Department department = getDepartmentById(id);
        department.setName(departmentRequest.getName());
        department.setStartDate(departmentRequest.getStartDate());
        em.merge(department);
        return department;
    }


}
