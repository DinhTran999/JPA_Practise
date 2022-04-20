package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.DepartmentRequest;
import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.entities.Project;
import com.axonactive.jpa.exeption.DepartmentException;
import com.axonactive.jpa.service.dto.DepartmentWithProjectsDTO;
import com.axonactive.jpa.service.dto.employee.EmployeeDTO;
import com.axonactive.jpa.service.mapper.ProjectMapper;
import com.axonactive.jpa.service.persistence.AbstractCRUDBean;
import com.axonactive.jpa.service.persistence.PersistenceService;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@RequestScoped
@Transactional
@Log4j2
public class DepartmentServiceImpl extends AbstractCRUDBean<Department> {
    @Inject
    PersistenceService<Department> persistenceService;

    @Inject
    EmployeeServiceImpl employeeService;

    @Inject
    ProjectMapper projectMapper;

    @Override
    protected PersistenceService<Department> getPersistenceService() {
        return persistenceService;
    }

    public Department saveDepartment (DepartmentRequest departmentRequest) {
        log.debug("save department with name: {} and startdate {}",departmentRequest.getName(),departmentRequest.getStartDate());
        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setStartDate(departmentRequest.getStartDate());
        return save(department);
    }

    public void deleteDepartment(int id) {
        Department department = findById(id);
        if(Objects.isNull(department)){
            log.error("Delete department has id: {} is not in data base",id);
            throw new DepartmentException(BAD_REQUEST,"Không tồn tại Department này");
        }
        if(isDepartmentHasEmployees(id)){
            log.error("Delete department with id: {} but has employee inside", id);
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Department có employee không thể xóa").build());
        }
        removeEntity(department);
    }

    public Department updateDepartment(int id, DepartmentRequest departmentRequest) {
        log.debug("update department has id {} with name: {} and startdate {}",id,departmentRequest.getName(),departmentRequest.getStartDate());
        Department department = findById(id);
        department.setName(departmentRequest.getName());
        department.setStartDate(departmentRequest.getStartDate());
        return update(department);
    }

    private boolean isDepartmentHasEmployees(int departmentId){
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployeeByDepartment(departmentId);
        return !employeeDTOList.isEmpty();
    }

    /**
     * Returns List of all departments together with projects
     * @return List of all departments together with projects,
     * if department do not have project then the project will be null
     * */
    public List<DepartmentWithProjectsDTO> getAllDepartmentWithProjects() {
        List<Department> departmentList = findAll();
        return departmentList.stream().map(d->{
            DepartmentWithProjectsDTO departmentWithProjectsDTO = new DepartmentWithProjectsDTO();
            departmentWithProjectsDTO.setId(d.getId());
            departmentWithProjectsDTO.setName(d.getName());
            departmentWithProjectsDTO.setStartDate(d.getStartDate());
            List<Project> projectList = persistenceService.getEntityManager().createQuery("from Project p where p.department.id=:departmentId", Project.class)
                    .setParameter("departmentId", d.getId()).getResultList();
            departmentWithProjectsDTO.setProjectList(projectMapper.ProjectsToProjectDtos(projectList));
            return departmentWithProjectsDTO;
        }).sorted(Comparator.comparing(DepartmentWithProjectsDTO::getName)).collect(Collectors.toList());
    }
}
