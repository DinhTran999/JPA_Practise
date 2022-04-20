package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.entities.HealthInsurance;
import com.axonactive.jpa.exeption.NoSuchHealthInsuranceException;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.HealthInsuranceService;
import com.axonactive.jpa.service.dto.HealthInsuranceDTO;
import com.axonactive.jpa.service.mapper.HealthInsuranceMapper;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

import static com.axonactive.jpa.constant.Constant.EMPLOYEE_ID_PARAMETER_NAME_SQL;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@RequestScoped
@Transactional
@Log4j2
public class HealthInsuranceServiceImpl implements HealthInsuranceService {

    @PersistenceContext(unitName = "jpa")
    EntityManager em;

    @Inject
    HealthInsuranceMapper healthInsuranceMapper;

    @Inject
    EmployeeServiceImpl employeeService;

    @Override
    public List<HealthInsuranceDTO> getHealthInsuranceByEmployeeId(int employeeId) {
        log.info("Get all health insurance card of employee has id: {}",employeeId);
        TypedQuery<HealthInsurance> namedQuery = em.createNamedQuery(HealthInsurance.GET_ALL_HEALTH_INSURANCE_BY_EMPLOYEE_ID, HealthInsurance.class);
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL,employeeId);
        return healthInsuranceMapper.HealthInsurancesToHealthInsuranceDTOs(namedQuery.getResultList());
    }

    @Override
    public HealthInsuranceDTO getHealthInsuranceByEmployeeIdAndHealthInsuranceId(int employeeId, int healthInsuranceId) {
        try {
            return healthInsuranceMapper.HealthInsuranceToHealthInsuranceDTO(getHealthInsuranceByEmployeeIdAndHealthInsuranceIdFromDataBase(employeeId,healthInsuranceId));
        } catch (NoResultException e){
            log.error("Get health insurance card has id{} of employee has id:{}",healthInsuranceId,employeeId);
            throw new NoSuchHealthInsuranceException();
        }
    }

    @Override
    public HealthInsuranceDTO addHealthInsurance(int employeeId, HealthInsuranceDTO healthInsuranceDTO) {
        log.debug("Add health insurance card to employeeId: {} with infor id: {}, code: {}, address: {}, registerHospital: {}, expirationDate: {}",
                employeeId,
                healthInsuranceDTO.getId(),
                healthInsuranceDTO.getCode(),
                healthInsuranceDTO.getAddress(),
                healthInsuranceDTO.getRegisterHospital(),
                healthInsuranceDTO.getExpirationDate());
        HealthInsurance healthInsurance = healthInsuranceMapper.HealthInsuranceDTOToHealthInsurance(healthInsuranceDTO);
        healthInsurance.setEmployee(employeeService.findById(employeeId));
        em.persist(healthInsurance);
        return healthInsuranceDTO;
    }

    @Override
    public void deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId(int employeeId, int healthInsuranceId) {
        log.info("Delete health insurance card has id: {} of employee has id {}",healthInsuranceId,employeeId);
        HealthInsurance healthInsurance = getHealthInsuranceByEmployeeIdAndHealthInsuranceIdFromDataBase(employeeId, healthInsuranceId);
        if(Objects.nonNull(healthInsurance)) em.remove(healthInsurance);
    }

    @Override
    public HealthInsuranceDTO updateHealthInsurance(int employeeId, int healthInsuranceId, HealthInsuranceDTO healthInsuranceDTO) {
        HealthInsurance healthInsurance = getHealthInsuranceByEmployeeIdAndHealthInsuranceIdFromDataBase(employeeId, healthInsuranceId);
        if(Objects.nonNull(healthInsurance)){
            log.debug("Update health insurance card has id: {} of employee has id: {} with infor code: {}, address: {}, registerHospital: {}, expirationDate: {}",
                    healthInsuranceId,
                    employeeId,
                    healthInsuranceDTO.getCode(),
                    healthInsuranceDTO.getAddress(),
                    healthInsuranceDTO.getRegisterHospital(),
                    healthInsuranceDTO.getExpirationDate());

            healthInsurance.setCode(healthInsuranceDTO.getCode());
            healthInsurance.setAddress(healthInsuranceDTO.getAddress());
            healthInsurance.setRegisterHospital(healthInsuranceDTO.getRegisterHospital());
            healthInsurance.setExpirationDate(healthInsuranceDTO.getExpirationDate());
            em.merge(healthInsurance);
            return healthInsuranceDTO;
        }
        log.error("could not found health insurance card has id: {} of employee has id: {}", healthInsuranceId,employeeId);
        throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Không tồn tại Health insurance card với Id: "+healthInsuranceId).build());
    }

    private HealthInsurance getHealthInsuranceByEmployeeIdAndHealthInsuranceIdFromDataBase(int employeeId, int healthInsuranceId){
        TypedQuery<HealthInsurance> namedQuery = em.createNamedQuery(HealthInsurance.GET_HEALTH_INSURANCE_BY_EMPLOYEE_ID_AND_HEALTH_INSURANCE_ID, HealthInsurance.class);
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL,employeeId);
        namedQuery.setParameter("healthInsuranceId",healthInsuranceId);
        return namedQuery.getSingleResult();
    }

}
