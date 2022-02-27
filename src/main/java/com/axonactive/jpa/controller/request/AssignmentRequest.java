package com.axonactive.jpa.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AssignmentRequest {
    @NotNull
    private int employeeId;
    @NotNull
    private int projectId;
}
