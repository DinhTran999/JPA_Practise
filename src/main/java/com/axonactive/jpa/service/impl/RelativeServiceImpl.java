package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.constant.Constant;
import com.axonactive.jpa.controller.request.RelativeRequest;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.entities.Relative;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.RelativeService;
import com.axonactive.jpa.service.dto.RelativeDTO;
import com.axonactive.jpa.service.mapper.RelativeMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static com.axonactive.jpa.constant.Constant.EMPLOYEE_ID_PARAMETER_NAME_SQL;
import static com.axonactive.jpa.constant.Constant.RELATIVE_ID_PARAMETER_NAME_SQL;

@Transactional
@RequestScoped
public class RelativeServiceImpl implements RelativeService {

    @PersistenceContext(unitName = "jpa")
    EntityManager em;

    @Inject
    RelativeMapper relativeMapper;

    @Inject
    EmployeeService employeeService;

    @Override
    public List<RelativeDTO> getAllRelativeByEmployeeId(int employeeId) {
        TypedQuery<Relative> namedQuery = em.createNamedQuery(Relative.GET_ALL_RELATIVE_BY_EMPLOYEE_ID, Relative.class);
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL, employeeId);
        return relativeMapper.RelativesToRelativeDtos(namedQuery.getResultList());
    }

    @Override
    public RelativeDTO getRelativeByEmployeeIdAndRelativeId(int employeeId, int relativeId) {
        TypedQuery<Relative> namedQuery = em.createNamedQuery(Relative.GET_RELATIVE_BY_EMPLOYEE_ID_AND_RELATIVE_ID, Relative.class);
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL, employeeId);
        namedQuery.setParameter(RELATIVE_ID_PARAMETER_NAME_SQL, relativeId);
        return relativeMapper.RelativeToRelativeDto(namedQuery.getSingleResult());
    }

    @Override
    public RelativeDTO addRelativeByEmployeeId(int employeeId, RelativeRequest relativeRequest) {
        Employee employee = em.createQuery("from Employee e where e.id = " + employeeId, Employee.class).getSingleResult();
        Relative relative = new Relative();
        relative.setEmployee(employee);
        relative.setFullName(relativeRequest.getFullName());
        relative.setGender(relativeRequest.getGender());
        relative.setPhoneNumber(relativeRequest.getPhoneNumber());
        relative.setRelationship(relativeRequest.getRelationship());
        em.persist(relative);
        return relativeMapper.RelativeToRelativeDto(relative);
    }

    @Override
    public void deleteRelativeByEmployeeIdAndRelativeId(int employeeId, int relativeId) {
        Relative relative = getRelativeByEmployeeIdAndRelativeIdHelper(employeeId, relativeId);
        if (Objects.nonNull(relative)) {
            em.remove(relative);
        }
    }

    @Override
    public RelativeDTO updateRelativeByEmployeeIdAndRelativeId(int employeeId, int relativeId, RelativeRequest relativeRequest) {
        Relative relative = getRelativeByEmployeeIdAndRelativeIdHelper(employeeId, relativeId);
        relative.setFullName(relativeRequest.getFullName());
        relative.setGender(relativeRequest.getGender());
        relative.setPhoneNumber(relativeRequest.getPhoneNumber());
        relative.setRelationship(relativeRequest.getRelationship());
        em.merge(relative);
        return relativeMapper.RelativeToRelativeDto(relative);
    }

    private Relative getRelativeByEmployeeIdAndRelativeIdHelper(int employeeId, int relativeId) {
        return em.createQuery("from Relative r where r.employee.id=:" + EMPLOYEE_ID_PARAMETER_NAME_SQL +
                        " and r.id=:" + RELATIVE_ID_PARAMETER_NAME_SQL, Relative.class)
                .setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL, employeeId)
                .setParameter(RELATIVE_ID_PARAMETER_NAME_SQL, relativeId)
                .getSingleResult();
    }
}
