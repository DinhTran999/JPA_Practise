package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.EmployeeOfDepartmentRequest;
import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.service.dto.employee.EmployeeDTO;
import com.axonactive.jpa.service.dto.EmployeeGroupByDepartmentDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployeeByDepartment(int departmentId);
    EmployeeDTO getEmployeeByDeptIdAndEmployeeId(int departmentId, int employeeId);
    EmployeeDTO addEmployeeByDepartmentId(int departmentId, EmployeeOfDepartmentRequest employeeRequest);
    void deleteEmployeeByDepartmentIdAndEmployeeId(int departmentId, int employeeId);
    EmployeeDTO updateEmployeeByDepartmentIdAndEmployeeId(int departmentId, int employeeId, EmployeeOfDepartmentRequest employeeRequest);

    List<EmployeeGroupByDepartmentDTO> getAllEmployeeGroupByDepartment();
    List<EmployeeDTO> getEmployeeByBirthMonth(int month);

    Employee getEmployeeByIdFromDataBase(int employeeId);
    EmployeeDTO getEmployeeById(int employeeId);
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployeeById(int employeeId);
    EmployeeDTO addEmployee(EmployeeRequest employeeRequest);
    EmployeeDTO updateEmployeeById(int employeeId, EmployeeRequest employeeRequest);
}
