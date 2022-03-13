package com.axonactive.jpa.controller.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class EmployeeRequest extends EmployeeOfDepartmentRequest {
    @NotNull
    private int departmentId;

}
