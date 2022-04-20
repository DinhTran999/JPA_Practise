package com.axonactive.jpa.service.dto.project;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectWithHoursAndCostDTO extends ProjectDTO{
    private String cost;
    private Integer hours;
}
