package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.ProjectRequest;
import com.axonactive.jpa.entities.Assignment;
import com.axonactive.jpa.entities.Project;
import com.axonactive.jpa.service.ProjectService;
import com.axonactive.jpa.service.dto.project.ProjectDTO;
import com.axonactive.jpa.service.dto.project.ProjectWithHoursAndCostDTO;
import com.axonactive.jpa.service.dto.project.ProjectWithDeptNameDTO;
import com.axonactive.jpa.service.dto.project.ProjectWithNumOfHourAndNumOfEmplDTO;
import com.axonactive.jpa.service.mapper.ProjectMapper;
import com.axonactive.jpa.service.mapper.ProjectWithDeptNameMapper;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;


@RequestScoped
@Transactional
@Log4j2
public class ProjectServiceImpl implements ProjectService {

    public static final int TOTAL_HOURS_PER_MONTH = 160;
    @PersistenceContext(unitName = "jpa")
    private EntityManager em;

    @Inject
    private DepartmentServiceImpl departmentService;

    @Inject
    private ProjectMapper projectMapper;

    @Inject
    private ProjectWithDeptNameMapper projectWithDeptNameMapper;


    @Override
    public ProjectDTO getProjectByDepartmentIdAndProjectId(int departmentId, int projectId) {
        return projectMapper.ProjectToProjectDto(getProjectByIdDepartmentIdAndProjectIdFromDatabase(departmentId, projectId));
    }

    @Override
    public List<ProjectDTO> getAllProjectByDepartment(int departmentId) {
        TypedQuery<Project> namedQuery = em.createNamedQuery(Project.GET_ALL, Project.class);
        namedQuery.setParameter("departmentId", departmentId);
        List<Project> projectList = namedQuery.getResultList();
        return projectMapper.ProjectsToProjectDtos(projectList);
    }

    @Override
    public ProjectDTO addProjectByDepartmentId(int departmentId, ProjectRequest projectRequest) {
        Project project = new Project();
        project.setArea(projectRequest.getArea());
        project.setName(projectRequest.getName());
        project.setDepartment(departmentService.findById(departmentId));
        em.persist(project);
        return projectMapper.ProjectToProjectDto(project);
    }

    @Override
    public void deleteProjectByDepartmentIdAndProjectId(int departmentId, int projectId) {
        Project project = getProjectByIdDepartmentIdAndProjectIdFromDatabase(departmentId, projectId);
        if (Objects.nonNull(project)) {
            em.remove(project);
        }

    }

    @Override
    public ProjectDTO updateProjectByDepartmentIdAndProjectId(int departmentId, int projectId, ProjectRequest projectRequest) {
        Project project = getProjectByIdDepartmentIdAndProjectIdFromDatabase(departmentId, projectId);
        project.setArea(projectRequest.getArea());
        project.setName(projectRequest.getName());
        em.merge(project);

        return projectMapper.ProjectToProjectDto(project);
    }

    @Override
    public List<ProjectDTO> getAllProject() {
        return projectMapper.ProjectsToProjectDtos(getAllProjectFromDatabase());
    }

    @Override
    public ProjectDTO getProjectById(int projectId) {
        return projectMapper.ProjectToProjectDto(getProjectByIdFromDatabase(projectId));
    }

    @Override
    public Project getProjectByIdFromDatabase(int projectId) {
        try {
            return em.createQuery("from Project p where p.id =:projectId", Project.class)
                    .setParameter("projectId", projectId).getSingleResult();
        } catch (NoResultException e) {
            log.error("get project id {} not in database", projectId);
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Không có Project với Id: " + projectId).build());
        }
    }

    @Override
    public ProjectDTO addProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setArea(projectRequest.getArea());
        project.setName(projectRequest.getName());

        Integer departmentId = projectRequest.getDepartmentId();
        if (Objects.nonNull(departmentId)) {
            project.setDepartment(departmentService.findById(departmentId));
        }
        em.persist(project);
        return projectMapper.ProjectToProjectDto(project);
    }

    /**
     *
     * @param projectId
     */
    @Override
    public void deleteProjectById(int projectId) {
        Project project = getProjectByIdFromDatabase(projectId);
        if (Objects.nonNull(project)) {
            em.remove(project);
        }
    }

    /**
     *
     * @param projectId
     * @param projectRequest
     * @return
     */

    @Override
    public ProjectDTO updateProject(int projectId, ProjectRequest projectRequest) {
        Project project = getProjectByIdFromDatabase(projectId);
        project.setName(projectRequest.getName());
        project.setArea(projectRequest.getArea());
        Integer departmentId = projectRequest.getDepartmentId();
        if (Objects.nonNull(departmentId)) {
            project.setDepartment(departmentService.findById(departmentId));
        }
        return projectMapper.ProjectToProjectDto(em.merge(project));
    }

    /**
     * Returns a List of all projects together with managed department name
     * @return a List of all projects together with managed department name
     */
    @Override
    public List<ProjectWithDeptNameDTO> getProjectWithDeptName() {
        List<Project> projectList = getAllProjectFromDatabase();
        projectList.sort(Comparator.comparing(Project::getName));
        return projectWithDeptNameMapper.ProjectsToProjectWithDeptNameDtos(projectList);
    }

    /**
     * Return a List all projects with number of employees and number of hours in a specific area
     * @param area the area of the project
     * @return a list all projects with number of employees and number of hours in a specific area
     * or empty list if do not have any project in this area.
     */
    @Override
    public List<ProjectWithNumOfHourAndNumOfEmplDTO> getAllProjectWithEffort(String area) {
//        Using Native Query
//        List<Object[]> resultList = em.createNativeQuery("select p.id,p.name,p.area,p.managed_department," +
//                " count(employee_Id) as numOfEmployees, sum(number_of_hour) as numOfHours" +
//                " from isc.project p, isc.assignment a" +
//                " where a.project_id=p.id" +
//                " group by p.id,p.name,p.area,p.managed_department").getResultList();
//
//        return resultList.stream().map(r -> {
//            ProjectWithNumOfHourAndNumOfEmplDTO projectWithEffort = new ProjectWithNumOfHourAndNumOfEmplDTO();
//            projectWithEffort.setId((Integer) r[0]);
//            projectWithEffort.setName((String) r[1]);
//            projectWithEffort.setArea((String) r[2]);
//            projectWithEffort.setDepartmentId((Integer) r[3]);
//            projectWithEffort.setNumOfEmployees(((BigInteger) r[4]).intValue());
//            if (Objects.nonNull(r[5])) {
//                projectWithEffort.setNumOfHours(((BigDecimal) r[5]).intValue());
//            }
//            return projectWithEffort;
//        }).collect(Collectors.toList());

        return em.createQuery("from Assignment", Assignment.class).getResultList().stream()
                .collect(Collectors.groupingBy(Assignment::getProject))
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getArea().equals(area))
                .map(projectEntry -> {
                    Project project = projectEntry.getKey();
                    List<Assignment> assignmentList = projectEntry.getValue();
                    ProjectWithNumOfHourAndNumOfEmplDTO projectWithEffort = new ProjectWithNumOfHourAndNumOfEmplDTO();
                    projectWithEffort.setId(project.getId());
                    projectWithEffort.setName(project.getName());
                    projectWithEffort.setArea(project.getArea());
                    projectWithEffort.setDepartmentId(project.getDepartment().getId());
                    projectWithEffort.setNumOfEmployees(assignmentList.size());
                    int numOfHours = (assignmentList.stream().mapToInt(Assignment::getNumberOfHour)).sum();
                    projectWithEffort.setNumOfHours(numOfHours);
                    return projectWithEffort;
                }).collect(Collectors.toList());

    }

    /**
     * Returns a List all projects with total cost (salary) and hours in specific area
     * @param area the area of the project
     * @return a List all projects with total cost in specific area
     * @throws java.io.IOException if the name of file does not exist
     * or empty list if there has no project in this area.
     */
    @Override
    public List<ProjectWithHoursAndCostDTO> getAllProjectWithHoursAndCost(String area) {
//        FileReader reader = new FileReader("d");
        return em.createQuery("from Assignment", Assignment.class).getResultList().stream()
                .collect(Collectors.groupingBy(Assignment::getProject))
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getArea().equals(area))
                .map(projectEntry -> {
                    Project project = projectEntry.getKey();
                    List<Assignment> assignmentList = projectEntry.getValue();
                    ProjectWithHoursAndCostDTO projectWithCostDTO = new ProjectWithHoursAndCostDTO();
                    projectWithCostDTO.setId(project.getId());
                    projectWithCostDTO.setName(project.getName());
                    projectWithCostDTO.setArea(project.getArea());
                    projectWithCostDTO.setDepartmentId(project.getDepartment().getId());
                    projectWithCostDTO.setHours(assignmentList.stream().mapToInt(Assignment::getNumberOfHour).sum());
                    double cost = assignmentList.stream().mapToDouble(a -> a.getEmployee().getSalary() * 1000 / TOTAL_HOURS_PER_MONTH * a.getNumberOfHour()).sum();
                    projectWithCostDTO.setCost(String.format("%.2f", cost));
                    return projectWithCostDTO;
                }).collect(Collectors.toList());
    }


    private List<Project> getAllProjectFromDatabase() {
        return em.createQuery("from Project", Project.class).getResultList();
    }

    private Project getProjectByIdDepartmentIdAndProjectIdFromDatabase(int departmentId, int projectId) {
        TypedQuery<Project> namedQuery = em.createNamedQuery(Project.GET_PROJECT_BY_ID, Project.class);
        namedQuery.setParameter("departmentId", departmentId);
        namedQuery.setParameter("projectId", projectId);
        return namedQuery.getSingleResult();

    }
}
