package com.axonactive.jpa.entities;

import com.axonactive.jpa.enumerate.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "department_location")
@NamedQuery(name = DepartmentLocation.GET_ALL, query = "from DepartmentLocation dl where dl.department.id = :departmentId")
@NamedQuery(name = DepartmentLocation.GET_LOCATION_BY_ID,
        query = "from DepartmentLocation dl where dl.department.id = :departmentId and dl.id = :locationId")
public class DepartmentLocation {

    private static final String QUALIFIER = "com.axonactive.jpa.entities";
    public static final String GET_ALL = QUALIFIER + "getAllLocationByDepartment";
    public static final String GET_LOCATION_BY_ID = QUALIFIER + "getLocationById";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    @Enumerated(EnumType.STRING)
    private Location location;

}
