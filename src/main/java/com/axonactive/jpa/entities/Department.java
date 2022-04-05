package com.axonactive.jpa.entities;

import com.axonactive.jpa.service.persistence.IEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "department")
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    public Integer getId(){
        return this.id;
    }

}
