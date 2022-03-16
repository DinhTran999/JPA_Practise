package com.axonactive.jpa.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.NotNull;

import java.time.LocalDate;

@Setter
@Getter
public class HealthInsuranceDTO {
    @NotNull
    private Integer id;
    @NotNull
    private String code;
    @NotNull
    private String address;
    @NotNull
    private String registerHospital;
    @NotNull
    private LocalDate expirationDate;
}
