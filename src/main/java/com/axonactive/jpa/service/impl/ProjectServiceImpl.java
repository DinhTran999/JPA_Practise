package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.ProjectRequest;
import com.axonactive.jpa.entities.Project;
import com.axonactive.jpa.service.ProjectService;
import com.axonactive.jpa.service.dto.ProjectDTO;
import com.axonactive.jpa.service.mapper.ProjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


@RequestScoped
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @PersistenceContext(unitName = "jpa")
    private EntityManager em;

    @Inject
    private DepartmentServiceImpl departmentService;

    private Project getProjectByIdDepartmentIdAndProjectIdHelper(int departmentId, int projectId){
        TypedQuery<Project> namedQuery = em.createNamedQuery(Project.GET_PROJECT_BY_ID, Project.class);
        namedQuery.setParameter("departmentId",departmentId);
        namedQuery.setParameter("projectId",projectId);
        return namedQuery.getSingleResult();

    }

    @Override
    public Project getProjectByIdHepler(int projectId){
        return em.createQuery("from Project p where p.id ="+projectId,Project.class).getSingleResult();
    }


    @Override
    public ProjectDTO getProjectById(int departmentId, int projectId) {
        return ProjectMapper.INSTANCE.ProjectToProjectDto(getProjectByIdDepartmentIdAndProjectIdHelper(departmentId,projectId));
    }

    @Override
    public List<ProjectDTO> getAllProjectByDepartment(int departmentId) {
        TypedQuery<Project> namedQuery = em.createNamedQuery(Project.GET_ALL, Project.class);
        namedQuery.setParameter("departmentId",departmentId);
        List<Project> projectList = namedQuery.getResultList();

        return ProjectMapper.INSTANCE.ProjectsToProjectDtos(projectList);
    }

    @Override
    public ProjectDTO addProject(int departmentId, ProjectRequest projectRequest) {
        Project project = new Project();
        project.setArea(projectRequest.getArea());
        project.setName(projectRequest.getName());
        project.setDepartment(departmentService.findById(departmentId));
        em.persist(project);
        return ProjectMapper.INSTANCE.ProjectToProjectDto(project);
    }

    @Override
    public void deleteProject(int departmentId, int projectId) {
        Project project = getProjectByIdDepartmentIdAndProjectIdHelper(departmentId, projectId);
        if(Objects.nonNull(project)){
            em.remove(project);
        }

    }

    @Override
    public ProjectDTO updateProject(int departmentId, int projectId, ProjectRequest projectRequest) {
        Project project = getProjectByIdDepartmentIdAndProjectIdHelper(departmentId, projectId);
        project.setArea(projectRequest.getArea());
        project.setName(projectRequest.getName());
        em.merge(project);

        return ProjectMapper.INSTANCE.ProjectToProjectDto(project);
    }
}
