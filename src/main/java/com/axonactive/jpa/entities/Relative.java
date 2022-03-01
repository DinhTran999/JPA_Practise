package com.axonactive.jpa.entities;

import com.axonactive.jpa.enumerate.Gender;
import com.axonactive.jpa.enumerate.Relationship;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "relatives")
@NamedQueries({
        @NamedQuery(name = Relative.GET_ALL_RELATIVE_BY_EMPLOYEE_ID,query = "from Relative r where r.employee.id =:employeeId"),
        @NamedQuery(name = Relative.GET_RELATIVE_BY_EMPLOYEE_ID_AND_RELATIVE_ID,query = "from Relative r where r.employee.id =:employeeId and r.id =:relativeId")
})
@Setter
@Getter
public class Relative {

    private static final String QUALIFIER = "com.axonactive.jpa.entities";
    public static final String GET_ALL_RELATIVE_BY_EMPLOYEE_ID = QUALIFIER + "getAllRelativeByEmployeeId";
    public static final String GET_RELATIVE_BY_EMPLOYEE_ID_AND_RELATIVE_ID = QUALIFIER + "getRelativeByEmployeeIdAndRelativeId";

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
