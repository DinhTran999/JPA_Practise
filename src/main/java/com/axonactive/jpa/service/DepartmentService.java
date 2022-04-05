package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.DepartmentRequest;
import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.service.dto.DepartmentWithProjectsDTO;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartment();
    Department getDepartmentById (int id);
    Department addDepartment(DepartmentRequest departmentRequest);
    void deleteDepartment(int id);
    Department updateDepartment(int id, DepartmentRequest departmentRequest);



}
