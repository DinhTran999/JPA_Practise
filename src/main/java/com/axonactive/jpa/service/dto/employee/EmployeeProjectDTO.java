package com.axonactive.jpa.service.dto.employee;

import com.axonactive.jpa.service.dto.RelativeDTO;
import com.axonactive.jpa.service.dto.project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeProjectDTO {
    private EmployeeDTO employee;
    private List<ProjectDTO> projectList;
}
