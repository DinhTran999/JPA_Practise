package com.axonactive.jpa.service.dto;

import com.axonactive.jpa.enumerate.Location;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class DepartmentLocationDTO {
    @NonNull
    private Location location;
}
