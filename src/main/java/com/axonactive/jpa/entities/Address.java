package com.axonactive.jpa.entities;


import com.axonactive.jpa.enumerate.AddressType;
import com.axonactive.jpa.enumerate.Province;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "address")
@ToString
@NamedQuery(name = Address.GET_ALL_ADDRESS_BY_EMPLOYEE_ID, query = "from Address a where a.employee.id=: employeeId")
@NamedQuery(name = Address.GET_ADDRESS_BY_EMPLOYEE_ID_AND_ADDRESS_ID, query = "from Address a where a.employee.id=: employeeId and a.id=:addressId")
public class Address {
    private static final String QUALIFIER = "com.axonactive.jpa.entities";
    public static final String GET_ALL_ADDRESS_BY_EMPLOYEE_ID = QUALIFIER + "getAllAddressByEmployeeId";
    public static final String GET_ADDRESS_BY_EMPLOYEE_ID_AND_ADDRESS_ID = QUALIFIER + "getAddressByEmployeeIdAndAddressId";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "district_level", nullable = false)
    @NotNull
    private String districtLevel;

    @NotNull
    @Column(name = "province_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private Province provinceLevel;

    @NotNull
    @Column(name = "address_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @CreationTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate modifiedDate;


}
