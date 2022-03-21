package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.DepartmentRequest;
import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.exeption.NoSuchDepartmentException;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.dto.EmployeeDTO;
import com.axonactive.jpa.service.persistence.AbstractCRUDBean;
import com.axonactive.jpa.service.persistence.PersistenceService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@RequestScoped
@Transactional
public class DepartmentServiceImpl extends AbstractCRUDBean<Department> {
    @Inject
    PersistenceService<Department> persistenceService;

    @Inject
    EmployeeService employeeService;

    @Override
    protected PersistenceService<Department> getPersistenceService() {
        return persistenceService;
    }

    public Department saveDepartment (DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setStartDate(departmentRequest.getStartDate());
        return save(department);
    }

    public void deleteDepartment(int id) {
        Department department = findById(id);
        if(Objects.isNull(department)){
            throw new NoSuchDepartmentException();
//            throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Không tồn tại Department này").build());
        }
        if(isDepartmentHasEmployees(id)){
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Department có employee không thể xóa").build());
        }
        removeEntity(department);
    }

    public Department updateDepartment(int id, DepartmentRequest departmentRequest) {
        Department department = findById(id);
        department.setName(departmentRequest.getName());
        department.setStartDate(departmentRequest.getStartDate());
        return update(department);
    }

    private boolean isDepartmentHasEmployees(int departmentId){
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployeeByDepartment(departmentId);
        return !employeeDTOList.isEmpty();
    }

}
