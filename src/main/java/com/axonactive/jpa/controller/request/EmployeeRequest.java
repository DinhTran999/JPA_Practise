package com.axonactive.jpa.controller.request;

import com.axonactive.jpa.enumerate.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Setter
@Getter
@ToString
public class EmployeeRequest {
    @NotNull
    private int departmentId;
    @NotNull
    protected String firstName;
    @NotNull
    protected String middleName;
    @NotNull
    protected String lastName;
    @Past
    @NotNull
    @JsonbDateFormat("yyyy-MM-dd")
    protected LocalDate dateOfBirth;
    @NotNull
    protected Gender gender;
    @NotNull
    protected double salary;
}
