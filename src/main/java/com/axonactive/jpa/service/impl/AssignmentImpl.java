package com.axonactive.jpa.service.impl;


import com.axonactive.jpa.controller.request.AssignmentRequest;
import com.axonactive.jpa.entities.Assignment;
import com.axonactive.jpa.service.AssignmentService;
import com.axonactive.jpa.service.dto.AssignmentDTO;
import com.axonactive.jpa.service.mapper.AssignmentMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class AssignmentImpl implements AssignmentService {

    @PersistenceContext(unitName = "jpa")
    private EntityManager em;

    @Inject
    private AssignmentMapper assignmentMapper;

    @Override
    public List<AssignmentDTO> getAssignments(AssignmentRequest assignmentRequest) {
        TypedQuery<Assignment> namedQuery = em.createNamedQuery(Assignment.GET_ALL, Assignment.class);
        namedQuery.setParameter("projectId", assignmentRequest.getProjectId());
        namedQuery.setParameter("employeeId", assignmentRequest.getEmployeeId());
        return assignmentMapper.AssignmentsToAssignmentDtos(namedQuery.getResultList());
    }

    @Override
    public AssignmentDTO getAssignmentById(int assignmentId) {
        return null;
    }

    @Override
    public AssignmentDTO addAssignment(AssignmentRequest employeeRequest) {
        return null;
    }

    @Override
    public void deleteAssignment(int assignmentId) {

    }

    @Override
    public AssignmentDTO updateAssignment(int assignmentId, AssignmentRequest employeeRequest) {
        return null;
    }
}
