package com.axonactive.jpa.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeRelativeDTO {
    private EmployeeDTO employee;
    private List<RelativeDTO> relatives;
}
