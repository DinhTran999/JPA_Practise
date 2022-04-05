package com.axonactive.jpa.service.dto.project;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectWithDeptNameDTO {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String area;

    private String managedDepartment;
}
