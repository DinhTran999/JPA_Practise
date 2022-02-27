package com.axonactive.jpa.controller.request;

import com.axonactive.jpa.enumerate.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Setter
@Getter
public class EmployeeRequest {
    @NotNull
    private String firstName;
    private String middleName;
    private String lastName;
    @Past
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Gender gender;
    private double salary;
}
