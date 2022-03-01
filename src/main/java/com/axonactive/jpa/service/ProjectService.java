package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.ProjectRequest;
import com.axonactive.jpa.entities.Project;
import com.axonactive.jpa.service.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    Project getProjectByIdHepler(int projectId);

    ProjectDTO getProjectById(int departmentId, int projectId);


    List<ProjectDTO> getAllProjectByDepartment(int departmentId);

    ProjectDTO addProject(int departmentId, ProjectRequest projectRequest);

    void deleteProject(int departmentId, int projectId);

    ProjectDTO updateProject(int departmentId, int projectId, ProjectRequest projectRequest);
}
