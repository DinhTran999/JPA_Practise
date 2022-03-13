package com.axonactive.jpa.service.impl;


import com.axonactive.jpa.controller.request.AssignmentRequest;
import com.axonactive.jpa.entities.Assignment;
import com.axonactive.jpa.service.AssignmentService;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.ProjectService;
import com.axonactive.jpa.service.dto.AssignmentDTO;
import com.axonactive.jpa.service.mapper.AssignmentMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static com.axonactive.jpa.constant.Constant.EMPLOYEE_ID_PARAMETER_NAME_SQL;

@RequestScoped
@Transactional
public class AssignmentImpl implements AssignmentService {

    @PersistenceContext(unitName = "jpa")
    private EntityManager em;

    @Inject
    private AssignmentMapper assignmentMapper;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private ProjectService projectService;

    @Override
    public List<AssignmentDTO> getAssignments(AssignmentRequest assignmentRequest) {
        TypedQuery<Assignment> namedQuery = em.createNamedQuery(Assignment.GET_ALL, Assignment.class);
        namedQuery.setParameter("projectId", assignmentRequest.getProjectId());
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL, assignmentRequest.getEmployeeId());
        return assignmentMapper.AssignmentsToAssignmentDtos(namedQuery.getResultList());
    }

    @Override
    public AssignmentDTO getAssignmentById(int assignmentId) {
        return assignmentMapper.AssignmentToAssignmentDto(getAssignmentByIdHelper(assignmentId));
    }

    @Override
    public AssignmentDTO addAssignment(AssignmentRequest assignmentRequest) {
        Assignment assignment = new Assignment();
        assignment.setEmployee(employeeService.getEmployeeByIdFromDataBase(assignmentRequest.getEmployeeId()));
        assignment.setProject(projectService.getProjectByIdHepler(assignmentRequest.getProjectId()));
        em.persist(assignment);
        return assignmentMapper.AssignmentToAssignmentDto(assignment);
    }

    @Override
    public void deleteAssignment(int assignmentId) {
        Assignment assignment = getAssignmentByIdHelper(assignmentId);
        if(Objects.nonNull(assignment)) em.remove(assignment);
    }

    @Override
    public AssignmentDTO updateAssignment(int assignmentId, AssignmentRequest assignmentRequest){
        Assignment assignment = getAssignmentByIdHelper(assignmentId);
        assignment.setEmployee(employeeService.getEmployeeByIdFromDataBase(assignmentRequest.getEmployeeId()));
        assignment.setProject(projectService.getProjectByIdHepler(assignmentRequest.getProjectId()));
        return assignmentMapper.AssignmentToAssignmentDto(em.merge(assignment));

    }

    private Assignment getAssignmentByIdHelper(int assignmentId){
        return em.createQuery("from Assignment a where a.id="+assignmentId,Assignment.class).getSingleResult();
    }
}
