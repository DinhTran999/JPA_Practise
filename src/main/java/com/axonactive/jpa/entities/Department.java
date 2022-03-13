package com.axonactive.jpa.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@Entity
@Table(name = "department")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@NamedQuery(name = Department.GET_BY_ID,query = "from Department d where d.id =:departmentId")
@NamedQuery(name = Department.GET_ALL,query = "from Department")
public class Department {

    private static final String QUALIFIER = "com.axonactive.jpa.entities";
    public static final String GET_ALL = "getAllDepartments";
    public static final String GET_BY_ID = QUALIFIER + "getDepartmentById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

}
