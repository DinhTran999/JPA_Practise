package com.axonactive.jpa.controller.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DepartmentRequest {
    @NonNull
    private String name;
    private LocalDate startDate;
}
