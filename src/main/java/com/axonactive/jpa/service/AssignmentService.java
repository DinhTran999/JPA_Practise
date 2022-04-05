package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.AssignmentRequest;
import com.axonactive.jpa.entities.Assignment;
import com.axonactive.jpa.service.dto.AssignmentDTO;

import java.util.List;

public interface AssignmentService {

    List<Assignment> getAssignmentsFromDatabase();
    List<AssignmentDTO> getAssignments();

    AssignmentDTO getAssignmentById(int assignmentId);

    AssignmentDTO addAssignment(AssignmentRequest employeeRequest);

    void deleteAssignment(int assignmentId);

    AssignmentDTO updateAssignment(int assignmentId, AssignmentRequest employeeRequest);
}
