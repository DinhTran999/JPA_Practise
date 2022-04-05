package com.axonactive.jpa.service.dto.employee;

import com.axonactive.jpa.service.dto.RelativeDTO;
import com.axonactive.jpa.service.dto.employee.EmployeeDTO;
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
