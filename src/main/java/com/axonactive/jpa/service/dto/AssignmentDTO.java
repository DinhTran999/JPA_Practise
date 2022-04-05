package com.axonactive.jpa.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AssignmentDTO {
    @NotNull
    private String employeeFullName;
    @NotNull
    private String projectName;
    private Integer numberOfHour;
}
