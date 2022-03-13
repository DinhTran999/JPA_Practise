package com.axonactive.jpa.service.dto;

import com.axonactive.jpa.enumerate.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Setter
@Getter
public class EmployeeDTO {
    @NotNull
    private Integer id;
    @NotNull
    private String fullName;
    @Past
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Gender gender;
    private double salary;
    private int departmentId;
}
