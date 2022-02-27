package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.DepartmentLocationRequest;
import com.axonactive.jpa.service.dto.DepartmentLocationDTO;

import java.util.List;

public interface DepartmentLocationService {
    List<DepartmentLocationDTO> getAllLocationByDepartment(int departmentId);
    DepartmentLocationDTO getDepartmentLocationById (int departmentId,int locationId);
    DepartmentLocationDTO addDepartmentLocation(int departmentId, DepartmentLocationRequest departmentLocationRequest);
    void deleteDepartmentLocation(int departmentId,int locationId);
    DepartmentLocationDTO updateDepartmentLocation(int departmentId,int locationId, DepartmentLocationRequest departmentLocationRequest);

}
