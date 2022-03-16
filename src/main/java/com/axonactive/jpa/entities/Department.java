package com.axonactive.jpa.entities;

import com.axonactive.jpa.service.persistence.IEntity;
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
public class Department implements IEntity {

    private static final String QUALIFIER = "com.axonactive.jpa.entities";
    public static final String GET_ALL = "getAllDepartments";
    public static final String GET_BY_ID = QUALIFIER + "getDepartmentById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    public int getId(){
        return this.id;
    }

}
