package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.DepartmentRequest;
import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.exeption.DepartmentException;
import com.axonactive.jpa.service.dto.employee.EmployeeDTO;
import com.axonactive.jpa.service.persistence.PersistenceService;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    Department departmentA = new Department(2,"A", LocalDate.parse("2020-09-08"));
    Department departmentB = new Department(3,"B", LocalDate.parse("2022-11-08"));
    Department departmentC = new Department(5,"C", LocalDate.parse("2020-12-01"));
    List<Department> expectedDepartmentList = Arrays.asList(departmentA, departmentB, departmentC);

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private PersistenceService<Department> persistenceService;

    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Department> mockQuery;

    @Test
    void getAllDepartment_ShouldReturnRightListDepartment() {
//        when(mockQuery.getResultList()).thenReturn(expectedDepartmentList);
//        when(entityManager.createNamedQuery(Department.GET_ALL,Department.class)).thenReturn(mockQuery);
        when(persistenceService.findAll()).thenReturn(expectedDepartmentList);
        List<Department> actualDepartmentList = departmentService.findAll();
        assertThat(expectedDepartmentList,containsInAnyOrder(actualDepartmentList.toArray()));
    }

    @Test
    void getDepartmentById_GetSpecificId_ShouldReturnDepartment() {
        int departmentId = 2;
        Department expectedDepartment = expectedDepartmentList.stream()
                .filter(d->d.getId()==departmentId)
                .findFirst().get();
//        when(entityManager.find(Department.class,departmentId)).thenReturn(expectedDepartment);
        when(persistenceService.findById(departmentId)).thenReturn(expectedDepartment);
        Department actualDepartment = departmentService.findById(departmentId);
        assertEquals(expectedDepartment,actualDepartment);
    }

    @Test
    void getDepartmentById_GetIdNotHasDepartment_ShouldReturnNull() {
        int departmentId = 6;
//        when(entityManager.find(Department.class,departmentId)).thenReturn(null);
        when(persistenceService.findById(departmentId)).thenReturn(null);
        Department actualDepartment = departmentService.findById(departmentId);
        assertNull(actualDepartment);
    }

    @Test
    void addDepartment_ReceivedDepartmentRequest_ShouldSaveDepartmentAndReturnDepartment() {
        //Data input
        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("A");
        departmentRequest.setStartDate(LocalDate.of(2020,11,20));

        //Expected Result
        Department expectedDepartment = new Department(4,"A",LocalDate.of(2020,11,20));

        //get actual result
        when(persistenceService.save(any(Department.class))).thenReturn(expectedDepartment);
        Department actualDepartment = departmentService.saveDepartment(departmentRequest);

        assertEquals(expectedDepartment,actualDepartment);
        //verify
//        verify(entityManager).persist(any(Department.class));

    }

    @Test
    void deleteDepartment_DeleteDepartmentHasNoEmployeeInside_ShouldDeleteDepartment() {
        int departmentId = 5;

        //department will be deleted
        Department departmentToBeDelete = new Department(departmentId,"A",LocalDate.of(2021,11,9));
        when(persistenceService.findById(departmentId)).thenReturn(departmentToBeDelete);

        //list employees of department will be deleted
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        when(employeeService.getAllEmployeeByDepartment(departmentId)).thenReturn(employeeDTOList);
        departmentService.deleteDepartment(departmentId);
        verify(persistenceService).removeEntity(departmentToBeDelete);

    }

    @Test
    void deleteDepartment_DeleteDepartmentHasEmployeeInside_ShouldThrowWebApplicationException() {
        int departmentId = 5;

        //department will be deleted
        Department departmentToBeDelete = new Department(departmentId,"A",LocalDate.of(2021,11,9));
        when(persistenceService.findById(departmentId)).thenReturn(departmentToBeDelete);

        //list employees of department will be deleted
        List<EmployeeDTO> employeeDTOList = Arrays.asList(new EmployeeDTO(), new EmployeeDTO());
        when(employeeService.getAllEmployeeByDepartment(departmentId)).thenReturn(employeeDTOList);

        try {
            departmentService.deleteDepartment(departmentId);
            fail("expected throw WebApplicationException");
        } catch (WebApplicationException ex){
            Response response = ex.getResponse();
            assertEquals(Response.Status.BAD_REQUEST,response.getStatusInfo());
            assertEquals("Department c?? employee kh??ng th??? x??a", response.getEntity().toString());
        }
//        assertThrows(WebApplicationException.class,()-> departmentService.deleteDepartment(departmentId));
    }

    @Test
    void deleteDepartment_DeleteDepartmentDoNotInDataBase_ShouldThrowNoSuchDepartmentException() {
        int departmentId = 7;
        //department will be deleted
        Department departmentToBeDelete = null;
        when(persistenceService.findById(departmentId)).thenReturn(departmentToBeDelete);
        assertThrows(DepartmentException.class,()-> departmentService.deleteDepartment(departmentId));
    }

    @Test
    void updateDepartment() {
        //input
        int departmentId = 4;
        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("ABC");
        departmentRequest.setStartDate(LocalDate.of(2020,11,23));

        //resource will be load for mock
        Department departmentBeforeUpdate = new Department(departmentId,"other name",LocalDate.of(2021,11,9));

        //mock database
        when(persistenceService.findById(departmentId)).thenReturn(departmentBeforeUpdate);

        //result
        Department expectedUpdatedDepartment = new Department();
        expectedUpdatedDepartment.setName(departmentRequest.getName());
        expectedUpdatedDepartment.setStartDate(departmentRequest.getStartDate());
        expectedUpdatedDepartment.setId(departmentId);

        when(persistenceService.update(expectedUpdatedDepartment)).thenReturn(expectedUpdatedDepartment);
        Department actualUpdateDepartment = departmentService.updateDepartment(departmentId, departmentRequest);

        verify(persistenceService).update(expectedUpdatedDepartment);
        assertEquals(actualUpdateDepartment,expectedUpdatedDepartment);


    }
}