package com.axonactive.jpa.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EmployeeGroupByDepartmentDTO {
    private String departmentName;
    private LocalDate startDate;
    private int numOfEmployee;
    private long numOfFemale;
    private long numOfMale;
    private long numOfU21;

}
