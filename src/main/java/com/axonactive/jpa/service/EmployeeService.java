package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.service.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployeeByDepartment(int departmentId);

    Employee getEmployeeByIdFromDataBase(int employeeId);
    EmployeeDTO getEmployeeById(int employeeId);
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployeeById(int employeeId);
    EmployeeDTO addEmployee(EmployeeRequest employeeRequest);
    EmployeeDTO updateEmployeeById(int employeeId, EmployeeRequest employeeRequest);
}
