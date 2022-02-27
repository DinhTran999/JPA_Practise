package com.axonactive.jpa.controller.request;

import com.axonactive.jpa.enumerate.Gender;
import com.axonactive.jpa.enumerate.Relationship;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.NotNull;

@Getter
@Setter
public class RelativeRequest {
    @NotNull
    private String fullName;
    private Gender gender;
    private String phoneNumber;
    @NotNull
    private Relationship relationship;
}
