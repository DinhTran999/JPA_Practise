package com.axonactive.jpa.service.dto;

import com.axonactive.jpa.enumerate.AddressType;
import com.axonactive.jpa.enumerate.Province;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.NotNull;



@Getter
@Setter
public class AddressDTO {
    @NotNull
    private String districtLevel;
    @NotNull
    private Province provinceLevel;
    @NotNull
    private AddressType addressType;
}
