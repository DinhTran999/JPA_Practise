package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.entities.HealthInsurance;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.HealthInsuranceService;
import com.axonactive.jpa.service.dto.HealthInsuranceDTO;
import com.axonactive.jpa.service.mapper.HealthInsuranceMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static com.axonactive.jpa.constant.Constant.EMPLOYEE_ID_PARAMETER_NAME_SQL;

@RequestScoped
@Transactional
public class HealthInsuranceServiceImpl implements HealthInsuranceService {

    @PersistenceContext(unitName = "jpa")
    EntityManager em;

    @Inject
    HealthInsuranceMapper healthInsuranceMapper;

    @Inject
    EmployeeService employeeService;

    @Override
    public List<HealthInsuranceDTO> getHealthInsuranceByEmployeeId(int employeeId) {
        TypedQuery<HealthInsurance> namedQuery = em.createNamedQuery(HealthInsurance.GET_ALL_HEALTH_INSURANCE_BY_EMPLOYEE_ID, HealthInsurance.class);
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL,employeeId);
        return healthInsuranceMapper.HealthInsurancesToHealthInsuranceDTOs(namedQuery.getResultList());
    }

    private HealthInsurance getHealthInsuranceByEmployeeIdAndHealthInsuranceIdHelper(int employeeId, int healthInsuranceId){
        TypedQuery<HealthInsurance> namedQuery = em.createNamedQuery(HealthInsurance.GET_HEALTH_INSURANCE_BY_EMPLOYEE_ID_AND_HEALTH_INSURANCE_ID, HealthInsurance.class);
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL,employeeId);
        namedQuery.setParameter("healthInsuranceId",healthInsuranceId);
        return namedQuery.getSingleResult();
    }

    @Override
    public HealthInsuranceDTO getHealthInsuranceByEmployeeIdAndHealthInsuranceId(int employeeId, int healthInsuranceId) {
        return healthInsuranceMapper.HealthInsuranceToHealthInsuranceDTO(getHealthInsuranceByEmployeeIdAndHealthInsuranceIdHelper(employeeId,healthInsuranceId));
    }

    @Override
    public HealthInsuranceDTO addHealthInsurance(int employeeId, HealthInsuranceDTO healthInsuranceDTO) {
        HealthInsurance healthInsurance = healthInsuranceMapper.HealthInsuranceDTOToHealthInsurance(healthInsuranceDTO);
        healthInsurance.setEmployee(employeeService.getEmployeeByIdFromDataBase(employeeId));
        em.persist(healthInsurance);
        return healthInsuranceMapper.HealthInsuranceToHealthInsuranceDTO(healthInsurance);
    }

    @Override
    public void deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId(int employeeId, int healthInsuranceId) {
        HealthInsurance healthInsurance = getHealthInsuranceByEmployeeIdAndHealthInsuranceIdHelper(employeeId, healthInsuranceId);
        if(Objects.nonNull(healthInsurance)) em.remove(healthInsurance);
    }

    @Override
    public HealthInsuranceDTO updateHealthInsurance(int employeeId, int healthInsuranceId, HealthInsuranceDTO healthInsuranceDTO) {
        HealthInsurance healthInsurance = getHealthInsuranceByEmployeeIdAndHealthInsuranceIdHelper(employeeId, healthInsuranceId);
        healthInsurance.setCode(healthInsuranceDTO.getCode());
        healthInsurance.setAddress(healthInsuranceDTO.getAddress());
        healthInsurance.setRegisterHospital(healthInsuranceDTO.getRegisterHospital());
        healthInsurance.setExpirationDate(healthInsuranceDTO.getExpirationDate());
        return healthInsuranceMapper.HealthInsuranceToHealthInsuranceDTO(em.merge(healthInsurance));
    }

}
