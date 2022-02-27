package com.axonactive.jpa.service.mapper;

import com.axonactive.jpa.controller.request.AssignmentRequest;
import com.axonactive.jpa.entities.Assignment;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.service.dto.AssignmentDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface AssignmentMapper {

    @Mapping(source = "project.name", target = "projectName")
    AssignmentDTO AssignmentToAssignmentDto(Assignment Assignment);

    List<AssignmentDTO> AssignmentsToAssignmentDtos(List<Assignment> assignmentList);

    @AfterMapping
    default void getEmployeeFullName(Assignment assignment, @MappingTarget AssignmentDTO assignmentDTO) {
        Employee employee = assignment.getEmployee();
        assignmentDTO.setEmployeeFullName(employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
    }

    Assignment AssignmentRequestToAssignment(AssignmentRequest assignmentRequest);
}
