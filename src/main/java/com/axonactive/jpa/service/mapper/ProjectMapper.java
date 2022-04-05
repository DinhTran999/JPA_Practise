package com.axonactive.jpa.service.mapper;

import com.axonactive.jpa.entities.Project;
import com.axonactive.jpa.service.dto.project.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ProjectMapper {
    @Mapping(source = "department.id", target = "departmentId")
    ProjectDTO ProjectToProjectDto(Project project);
    List<ProjectDTO> ProjectsToProjectDtos (List<Project> projectList);


}
