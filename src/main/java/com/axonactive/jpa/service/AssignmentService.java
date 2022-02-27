package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.AssignmentRequest;
import com.axonactive.jpa.service.dto.AssignmentDTO;

import java.util.List;

public interface AssignmentService {
    List<AssignmentDTO> getAssignments(AssignmentRequest assignmentRequest);

    AssignmentDTO getAssignmentById(int assignmentId);

    AssignmentDTO addAssignment(AssignmentRequest employeeRequest);

    void deleteAssignment(int assignmentId);

    AssignmentDTO updateAssignment(int assignmentId, AssignmentRequest employeeRequest);
}
