package com.axonactive.jpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "assignment")
@NamedQuery(name = Assignment.GET_ALL_BY_PROJECT_ID_AND_EMPLOYEE_ID, query = "from Assignment a where a.project.id = :projectId and a.employee.id = :employeeId")
@NamedQuery(name = Assignment.GET_ASSIGNMENT_BY_ID, query = "from Assignment a where a.id = :assignmentId")
@NamedQuery(name = Assignment.GET_ALL,query = "from Assignment")
public class Assignment {

    private static final String QUALIFIER = "com.axonactive.jpa.entities";
    public static final String GET_ALL_BY_PROJECT_ID_AND_EMPLOYEE_ID = QUALIFIER + "getAllAssignmentsByProjectIdAndEmployeeId";
    public static final String GET_ALL = QUALIFIER +"getAllAssignments";
    public static final String GET_ASSIGNMENT_BY_ID = QUALIFIER + "getAssignmentById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "number_of_hour")
    private Integer numberOfHour;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;
}
