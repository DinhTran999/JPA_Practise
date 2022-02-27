package com.axonactive.jpa.entities;

import com.axonactive.jpa.enumerate.Gender;
import com.axonactive.jpa.enumerate.Relationship;

import javax.persistence.*;

@Entity
@Table(name = "relatives")
public class Relative {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "full_name", nullable = false, columnDefinition = "varchar(100)")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(50)")
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "varchar(15)")
    @Enumerated(EnumType.STRING)
    private Relationship relationship;
}
