package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.ProjectRequest;
import com.axonactive.jpa.entities.Project;
import com.axonactive.jpa.service.dto.project.ProjectDTO;
import com.axonactive.jpa.service.dto.project.ProjectWithHoursAndCostDTO;
import com.axonactive.jpa.service.dto.project.ProjectWithDeptNameDTO;
import com.axonactive.jpa.service.dto.project.ProjectWithNumOfHourAndNumOfEmplDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO getProjectByDepartmentIdAndProjectId(int departmentId, int projectId);
    List<ProjectDTO> getAllProjectByDepartment(int departmentId);
    ProjectDTO addProjectByDepartmentId(int departmentId, ProjectRequest projectRequest);
    void deleteProjectByDepartmentIdAndProjectId(int departmentId, int projectId);
    ProjectDTO updateProjectByDepartmentIdAndProjectId(int departmentId, int projectId, ProjectRequest projectRequest);


    Project getProjectByIdFromDatabase(int projectId);
    ProjectDTO getProjectById(int projectId);
    List<ProjectDTO> getAllProject();
    ProjectDTO addProject(ProjectRequest projectRequest);

    void deleteProjectById(int projectId);

    ProjectDTO updateProject(int projectId, ProjectRequest projectRequest);

    List<ProjectWithDeptNameDTO> getProjectWithDeptName();

    List<ProjectWithNumOfHourAndNumOfEmplDTO> getAllProjectWithEffort(String area);

    List<ProjectWithHoursAndCostDTO> getAllProjectWithHoursAndCost(String area);
}
