package com.axonactive.jpa.service.mapper;


import com.axonactive.jpa.entities.HealthInsurance;
import com.axonactive.jpa.service.dto.HealthInsuranceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface HealthInsuranceMapper {

    HealthInsuranceDTO HealthInsuranceToHealthInsuranceDTO (HealthInsurance healthInsurance);

    List<HealthInsuranceDTO> HealthInsurancesToHealthInsuranceDTOs(List<HealthInsurance> healthInsuranceList);

    HealthInsurance HealthInsuranceDTOToHealthInsurance (HealthInsuranceDTO healthInsuranceDTO);

}
