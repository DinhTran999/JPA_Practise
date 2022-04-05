package com.axonactive.jpa.entities;

import com.axonactive.jpa.enumerate.Nationality;
import com.axonactive.jpa.enumerate.converter.NationalityAttributeConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    private String name;
    @Transient
    private Integer age;
    @Embedded
    private ContactInfo contactInfo;
    @Convert(converter = NationalityAttributeConverter.class)
    private Nationality nationality;
}
