package com.axonactive.jpa.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectDTO {
    @NotNull
    private String name;
    @NotNull
    private String area;
}
