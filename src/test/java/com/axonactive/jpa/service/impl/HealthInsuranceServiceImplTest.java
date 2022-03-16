package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.entities.HealthInsurance;
import com.axonactive.jpa.enumerate.Gender;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.dto.HealthInsuranceDTO;
import com.axonactive.jpa.service.mapper.HealthInsuranceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthInsuranceServiceImplTest {

    @InjectMocks
    HealthInsuranceServiceImpl healthInsuranceService;

    @Mock
    HealthInsuranceMapper healthInsuranceMapper;

    @Mock
    EmployeeService employeeService;

    @Mock
    EntityManager entityManager;

    @Mock
    TypedQuery<HealthInsurance> typedQuery;

    Employee employee;
    HealthInsurance healthInsurance1;
    HealthInsurance healthInsurance2;
    List<HealthInsurance> healthInsuranceList;

    @BeforeEach
    public void init(){
        employee = new Employee(3, "E", "G", "F",
                LocalDate.of(2021, 9, 11)
                , Gender.FEMALE, 1200, new Department(8, "DeptC", LocalDate.of(2018, 1, 10)));

        healthInsurance1 = new HealthInsurance(1,employee,"A","Tan Binh","BV Quan 3",LocalDate.of(2021,1,2));
        healthInsurance2 = new HealthInsurance(2,employee,"B","Phú Nhuận","BV Quan 10",LocalDate.of(2019,10,8));
        healthInsuranceList = Arrays.asList(healthInsurance1, healthInsurance2);
    }


    @Test
    void getHealthInsuranceByEmployeeId_RightEmployeeId_ShouldReturnHealthInsuranceListOfEmployee() {
        int employeeId = 3;
        List<HealthInsurance> expectedHealthInsuranceList = this.healthInsuranceList.stream()
                .filter(h -> h.getEmployee().getId() == employeeId).collect(Collectors.toList());
        when(entityManager.createNamedQuery(HealthInsurance.GET_ALL_HEALTH_INSURANCE_BY_EMPLOYEE_ID,HealthInsurance.class))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expectedHealthInsuranceList);

        List<HealthInsuranceDTO> expectedHealthInsuranceDTOList = new ArrayList<>();
        healthInsuranceList.forEach(healthInsurance->{
            expectedHealthInsuranceDTOList.add(heathInsuranceToHealthInsuranceDTO(healthInsurance));
        });

        when(healthInsuranceMapper.HealthInsurancesToHealthInsuranceDTOs(expectedHealthInsuranceList))
                .thenReturn(expectedHealthInsuranceDTOList);
        List<HealthInsuranceDTO> actualHealthInsuranceDTOList = healthInsuranceService.getHealthInsuranceByEmployeeId(employeeId);
        assertEquals(expectedHealthInsuranceDTOList,actualHealthInsuranceDTOList);
    }

    @Test
    void getHealthInsuranceByEmployeeId_WrongEmployeeId_ShouldReturnEmptyList() {
        int employeeId = 4;
        List<HealthInsurance> expectedHealthInsuranceList = this.healthInsuranceList.stream()
                .filter(h -> h.getEmployee().getId() == employeeId).collect(Collectors.toList());
        when(entityManager.createNamedQuery(HealthInsurance.GET_ALL_HEALTH_INSURANCE_BY_EMPLOYEE_ID,HealthInsurance.class))
                .thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expectedHealthInsuranceList);

        //empty List
        List<HealthInsuranceDTO> expectedHealthInsuranceDTOList = new ArrayList<>();

        when(healthInsuranceMapper.HealthInsurancesToHealthInsuranceDTOs(expectedHealthInsuranceList))
                .thenReturn(expectedHealthInsuranceDTOList);
        List<HealthInsuranceDTO> actualHealthInsuranceDTOList = healthInsuranceService.getHealthInsuranceByEmployeeId(employeeId);
        assertEquals(expectedHealthInsuranceDTOList,actualHealthInsuranceDTOList);
    }

    @Test
    void getHealthInsuranceByEmployeeIdAndHealthInsuranceId_RightEmployeeIdAndHealthInsuranceId_ShouldReturnHealthInsurance() {
        int employeeId = 3;
        int healthInsuranceId =2;
        HealthInsurance expectedHealthInsurance = healthInsuranceList.stream()
                .filter(h -> h.getEmployee().getId() == employeeId && h.getId() == healthInsuranceId)
                .findFirst().get();

        when(entityManager.createNamedQuery(HealthInsurance.GET_HEALTH_INSURANCE_BY_EMPLOYEE_ID_AND_HEALTH_INSURANCE_ID,HealthInsurance.class))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(expectedHealthInsurance);
        HealthInsuranceDTO expectedHealthInsuranceDTO = heathInsuranceToHealthInsuranceDTO(expectedHealthInsurance);

        when(healthInsuranceMapper.HealthInsuranceToHealthInsuranceDTO(expectedHealthInsurance))
                .thenReturn(expectedHealthInsuranceDTO);
        HealthInsuranceDTO actualHealthInsuranceDTO = healthInsuranceService.getHealthInsuranceByEmployeeIdAndHealthInsuranceId(employeeId, healthInsuranceId);
        assertEquals(expectedHealthInsuranceDTO,actualHealthInsuranceDTO);
    }

    @Test
    void getHealthInsuranceByEmployeeIdAndHealthInsuranceId_RightEmployeeIdAndWrongHealthInsuranceId_ShouldReturnNull() {
        when(entityManager.createNamedQuery(HealthInsurance.GET_HEALTH_INSURANCE_BY_EMPLOYEE_ID_AND_HEALTH_INSURANCE_ID,HealthInsurance.class))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(null);
        HealthInsuranceDTO actualHealthInsuranceDTO = healthInsuranceService.getHealthInsuranceByEmployeeIdAndHealthInsuranceId(anyInt(), anyInt());
        assertNull(actualHealthInsuranceDTO);
    }

    @Test
    void addHealthInsurance_rightHeathDTO_ShouldAddHealthInsurance() {
        HealthInsuranceDTO healthInsuranceDTO = new HealthInsuranceDTO();
        healthInsuranceDTO.setId(5);
        healthInsuranceDTO.setCode("xyz");
        healthInsuranceDTO.setAddress("Nhà Bè");
        healthInsuranceDTO.setRegisterHospital("Bệnh Viện Hoàn Mỹ");
        healthInsuranceDTO.setExpirationDate(LocalDate.of(2021,11,9));

        int employeeId = 3;
        when(employeeService.getEmployeeByIdFromDataBase(employeeId))
                .thenReturn(employee);

        HealthInsurance healthInsuranceToBeAdd = new HealthInsurance();
        healthInsuranceToBeAdd.setId(healthInsuranceDTO.getId());
        healthInsuranceToBeAdd.setCode(healthInsuranceDTO.getCode());
        healthInsuranceToBeAdd.setAddress(healthInsuranceDTO.getAddress());
        healthInsuranceToBeAdd.setRegisterHospital(healthInsuranceDTO.getRegisterHospital());
        healthInsuranceToBeAdd.setExpirationDate(healthInsuranceDTO.getExpirationDate());
        when(healthInsuranceMapper.HealthInsuranceDTOToHealthInsurance(healthInsuranceDTO))
                .thenReturn(healthInsuranceToBeAdd);

        HealthInsuranceDTO returnedHealthInsuranceDTO = healthInsuranceService.addHealthInsurance(employeeId, healthInsuranceDTO);


        assertEquals(healthInsuranceDTO,returnedHealthInsuranceDTO);
        assertEquals(healthInsuranceToBeAdd.getEmployee().getFirstName(),employee.getFirstName());
        verify(entityManager).persist(healthInsuranceToBeAdd);

    }

    @Test
    void deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId_rightId_ShouldDeleteHealthInsurance() {
        int employeeId = 3;
        int healthInsuranceId = 1;
        when(entityManager.createNamedQuery(HealthInsurance.GET_HEALTH_INSURANCE_BY_EMPLOYEE_ID_AND_HEALTH_INSURANCE_ID,HealthInsurance.class))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(healthInsurance1);
        healthInsuranceService.deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId(employeeId,healthInsuranceId);
        verify(entityManager).remove(healthInsurance1);

    }

    @Test
    void deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId_wrongHealthInsuranceId_ShouldNotDeleteHealthInsurance() {
        int employeeId = 3;
        int healthInsuranceId = 10;
        when(entityManager.createNamedQuery(HealthInsurance.GET_HEALTH_INSURANCE_BY_EMPLOYEE_ID_AND_HEALTH_INSURANCE_ID,HealthInsurance.class))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(null);
        healthInsuranceService.deleteHealthInsuranceByEmployeeIdAndHealthInsuranceId(employeeId,healthInsuranceId);
        verify(entityManager,never()).remove(healthInsurance1);

    }

    @Test
    void updateHealthInsurance_rightHealthInsuranceId_shouldUpdateHealthInsurance() {
        int employeeId = 3;
        int healthInsuranceId = 2;
        HealthInsuranceDTO healthInsuranceDTO = new HealthInsuranceDTO();
        healthInsuranceDTO.setId(healthInsuranceId);
        healthInsuranceDTO.setCode("Alo");
        healthInsuranceDTO.setAddress("Quận 7");
        healthInsuranceDTO.setRegisterHospital("Bệnh viện dã chiến");
        healthInsuranceDTO.setExpirationDate(LocalDate.of(2022,11,9));

        when(entityManager.createNamedQuery(HealthInsurance.GET_HEALTH_INSURANCE_BY_EMPLOYEE_ID_AND_HEALTH_INSURANCE_ID,HealthInsurance.class))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(healthInsurance2);

        HealthInsurance expectedUpdatedHealthInsurance = healthInsurance2;

        expectedUpdatedHealthInsurance.setCode(healthInsuranceDTO.getCode());
        expectedUpdatedHealthInsurance.setAddress(healthInsuranceDTO.getAddress());
        expectedUpdatedHealthInsurance.setRegisterHospital(healthInsuranceDTO.getRegisterHospital());
        expectedUpdatedHealthInsurance.setExpirationDate(healthInsuranceDTO.getExpirationDate());

        HealthInsuranceDTO returnedHealthInsuranceDTO = healthInsuranceService.updateHealthInsurance(employeeId, healthInsuranceId, healthInsuranceDTO);
        assertEquals(healthInsuranceDTO,returnedHealthInsuranceDTO);
        verify(entityManager).merge(expectedUpdatedHealthInsurance);
    }

    @Test
    void updateHealthInsurance_wrongHealthInsuranceId_shouldThrowWebApplicationException() {
        int employeeId = 3;
        int healthInsuranceId = 10;
        HealthInsuranceDTO healthInsuranceDTO = new HealthInsuranceDTO();
        healthInsuranceDTO.setId(healthInsuranceId);
        healthInsuranceDTO.setCode("Alo");
        healthInsuranceDTO.setAddress("Quận 7");
        healthInsuranceDTO.setRegisterHospital("Bệnh viện dã chiến");
        healthInsuranceDTO.setExpirationDate(LocalDate.of(2022,11,9));

        when(entityManager.createNamedQuery(HealthInsurance.GET_HEALTH_INSURANCE_BY_EMPLOYEE_ID_AND_HEALTH_INSURANCE_ID,HealthInsurance.class))
                .thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(null);

        try{
            healthInsuranceService.updateHealthInsurance(employeeId,healthInsuranceId,healthInsuranceDTO);
            fail("Expect to throw WebApplicationException");
        } catch (WebApplicationException e){
            Response response = e.getResponse();
            assertEquals(Response.Status.BAD_REQUEST,response.getStatusInfo());
            assertEquals("Không tồn tại Health insurance card với Id: "+healthInsuranceId,response.getEntity().toString());
            verify(entityManager,never()).merge(any(HealthInsurance.class));
        }
    }

    private HealthInsuranceDTO heathInsuranceToHealthInsuranceDTO(HealthInsurance healthInsurance){
        HealthInsuranceDTO healthInsuranceDTO = new HealthInsuranceDTO();
        healthInsuranceDTO.setAddress(healthInsurance.getAddress());
        healthInsuranceDTO.setCode(healthInsurance.getCode());
        healthInsuranceDTO.setExpirationDate(healthInsurance.getExpirationDate());
        healthInsuranceDTO.setRegisterHospital(healthInsurance.getRegisterHospital());
        healthInsuranceDTO.setId(healthInsurance.getId());
        return healthInsuranceDTO;
    }
}