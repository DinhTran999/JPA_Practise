package com.axonactive.jpa.service.dto.project;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectDTO {
    protected Integer id;
    @NotNull
    protected String name;
    @NotNull
    protected String area;

    protected Integer departmentId;
}
