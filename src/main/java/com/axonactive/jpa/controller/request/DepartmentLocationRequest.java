package com.axonactive.jpa.controller.request;

import com.axonactive.jpa.enumerate.Location;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class DepartmentLocationRequest {
    @NonNull
    private Location location;
}
