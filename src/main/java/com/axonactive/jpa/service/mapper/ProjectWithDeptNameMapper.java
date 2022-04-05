package com.axonactive.jpa.service.mapper;

import com.axonactive.jpa.entities.Project;
import com.axonactive.jpa.service.dto.project.ProjectWithDeptNameDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ProjectWithDeptNameMapper {
    @Mapping(source = "department.name", target = "managedDepartment")
    ProjectWithDeptNameDTO ProjectToProjectWithDeptNameDto(Project project);
    List<ProjectWithDeptNameDTO> ProjectsToProjectWithDeptNameDtos (List<Project> projectList);


}
