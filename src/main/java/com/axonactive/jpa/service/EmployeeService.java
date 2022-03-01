package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.service.dto.EmployeeDTO;
import com.axonactive.jpa.service.dto.EmployeeGroupByDepartmentDTO;
import com.axonactive.jpa.service.dto.RelativeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployeeByDepartment(int departmentId);

    EmployeeDTO getEmployeeByDeptIdAndEmployeeId(int departmentId, int employeeId);

    EmployeeDTO addEmployee(int departmentId, EmployeeRequest employeeRequest);

    void deleteEmployee(int departmentId, int employeeId);

    EmployeeDTO updateEmployee(int departmentId, int employeeId, EmployeeRequest employeeRequest);

    List<EmployeeGroupByDepartmentDTO> getAllEmployeeGroupByDepartment();

    List<EmployeeDTO> getEmployeeByBirthMonth(int month);



}
