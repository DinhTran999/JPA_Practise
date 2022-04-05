package com.axonactive.jpa.service.impl;


import com.axonactive.jpa.controller.request.AssignmentRequest;
import com.axonactive.jpa.entities.Assignment;
import com.axonactive.jpa.service.AssignmentService;
import com.axonactive.jpa.service.ProjectService;
import com.axonactive.jpa.service.dto.AssignmentDTO;
import com.axonactive.jpa.service.mapper.AssignmentMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequestScoped
@Transactional
public class AssignmentImpl implements AssignmentService {

    @PersistenceContext(unitName = "jpa")
    private EntityManager em;

    @Inject
    private AssignmentMapper assignmentMapper;

    @Inject
    private EmployeeServiceImpl employeeService;

    @Inject
    private ProjectService projectService;

    @Override
    public List<AssignmentDTO> getAssignments() {
        return assignmentMapper.AssignmentsToAssignmentDtos(getAssignmentsFromDatabase());
    }

    @Override
    public AssignmentDTO getAssignmentById(int assignmentId) {
        return assignmentMapper.AssignmentToAssignmentDto(getAssignmentByIdFromDatabase(assignmentId));
    }

    @Override
    public AssignmentDTO addAssignment(AssignmentRequest assignmentRequest) {
        Assignment assignment = new Assignment();
        assignment.setNumberOfHour(assignmentRequest.getNumberOfHour());
        assignment.setEmployee(employeeService.findById(assignmentRequest.getEmployeeId()));
        assignment.setProject(projectService.getProjectByIdFromDatabase(assignmentRequest.getProjectId()));
        em.persist(assignment);
        return assignmentMapper.AssignmentToAssignmentDto(assignment);
    }

    @Override
    public void deleteAssignment(int assignmentId) {
        Assignment assignment = getAssignmentByIdFromDatabase(assignmentId);
        if(Objects.nonNull(assignment)) em.remove(assignment);
    }

    @Override
    public AssignmentDTO updateAssignment(int assignmentId, AssignmentRequest assignmentRequest){
        Assignment assignment = getAssignmentByIdFromDatabase(assignmentId);
        assignment.setNumberOfHour(assignmentRequest.getNumberOfHour());
        assignment.setEmployee(employeeService.findById(assignmentRequest.getEmployeeId()));
        assignment.setProject(projectService.getProjectByIdFromDatabase(assignmentRequest.getProjectId()));
        return assignmentMapper.AssignmentToAssignmentDto(em.merge(assignment));

    }

    private Assignment getAssignmentByIdFromDatabase(int assignmentId){
        return em.createQuery("from Assignment a where a.id="+assignmentId,Assignment.class).getSingleResult();
    }

    @Override
    public List<Assignment> getAssignmentsFromDatabase() {
        return em.createNamedQuery(Assignment.GET_ALL, Assignment.class)
                .getResultList();
    }
}
