package com.axonactive.jpa.controller.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.NotNull;

@Getter
@Setter
public class UserRequest {
    @NotNull
    private String name;
    @NotNull
    private String password;
}
