package com.axonactive.jpa.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class ContactInfo {
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String skype;
}
