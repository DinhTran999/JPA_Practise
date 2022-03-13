package com.axonactive.jpa.service.mapper;

import com.axonactive.jpa.entities.Project;
import com.axonactive.jpa.service.dto.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class );

    ProjectDTO ProjectToProjectDto(Project project);
    List<ProjectDTO> ProjectsToProjectDtos (List<Project> projectList);

}
