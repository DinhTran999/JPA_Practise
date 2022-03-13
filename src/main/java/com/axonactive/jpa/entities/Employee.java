package com.axonactive.jpa.entities;

import com.axonactive.jpa.enumerate.Gender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "employee")
@NamedQuery(name = Employee.GET_ALL_BY_DEPT_ID_AND_EMPLOYEE_ID, query = "from Employee e where e.department.id = :departmentId")
@NamedQuery(name = Employee.GET_EMPLOYEE_BY_DEPTID_AND_EMPLOYEEID, query = "from Employee e where e.department.id = :departmentId and e.id = :employeeId")
@NamedQuery(name = Employee.GET_EMPLOYEE_BY_ID, query = "from Employee e where e.id = :employeeId")
@NamedQuery(name = Employee.GET_ALL, query = "from Employee")
public class Employee {
    private static final String QUALIFIER = "com.axonactive.jpa.entities";
    public static final String GET_ALL_BY_DEPT_ID_AND_EMPLOYEE_ID = QUALIFIER + "getAllByDepartment";
    public static final String GET_ALL = QUALIFIER + "getAll";
    public static final String GET_EMPLOYEE_BY_DEPTID_AND_EMPLOYEEID = QUALIFIER + "getEmployeeByDeptIdAndEmployeeId";
    public static final String GET_EMPLOYEE_BY_ID = QUALIFIER + "getEmployeeById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", nullable = false, columnDefinition = "varchar(20)")
    private String firstName;

    @Column(name = "middle_name", nullable = false, columnDefinition = "varchar(20)")
    @NotNull
    private String middleName;

    @Column(name = "last_name", nullable = false, columnDefinition = "varchar(20)")
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private Department department;

}
