package com.axonactive.jpa.service.mapper;

import com.axonactive.jpa.controller.request.EmployeeOfDepartmentRequest;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.service.dto.employee.EmployeeDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDTO EmployeeToEmployeeDto(Employee employee);

    List<EmployeeDTO> EmployeesToEmployeeDtos(List<Employee> employeeList);

    Employee EmployeeRequestToEmployee(EmployeeOfDepartmentRequest employeeRequest);

    @AfterMapping
    default void getFullName(Employee employee, @MappingTarget EmployeeDTO employeeDTO) {
        employeeDTO.setFullName(employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
    }

}
