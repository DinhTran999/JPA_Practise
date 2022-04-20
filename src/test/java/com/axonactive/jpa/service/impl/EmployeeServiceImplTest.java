package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.enumerate.Gender;
import com.axonactive.jpa.service.DepartmentService;
import com.axonactive.jpa.service.dto.employee.EmployeeDTO;
import com.axonactive.jpa.service.mapper.EmployeeMapper;
import com.axonactive.jpa.service.persistence.PersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Mock
    PersistenceService persistenceService;

    @Mock
    EntityManager entityManager;

    @Mock
    EmployeeMapper employeeMapper;

    @Mock
    TypedQuery<Employee> typedQuery;

    @Mock
    DepartmentServiceImpl departmentService;

    Employee employee1;
    Employee employee2;
    List<Employee> employeeList;

    @BeforeEach
    public void init() {
        employee1 = new Employee(2, "A", "B", "C",
                LocalDate.of(2020, 11, 9)
                , Gender.FEMALE, 1000, new Department(10, "DeptA", LocalDate.of(2019, 10, 8)));

        employee2 = new Employee(3, "E", "G", "F",
                LocalDate.of(2021, 9, 11)
                , Gender.FEMALE, 1200, new Department(8, "DeptC", LocalDate.of(2018, 1, 10)));
        employeeList = Arrays.asList(employee1, employee2);
    }

    @Test
    void getEmployeeByIdFromDataBase_FindRightId_ShouldReturnEmployee() {
        int employeeIdToBeFind = 3;
        Employee expectedEmployee = getEmployeeFromTestListById(employeeIdToBeFind);
        when(persistenceService.findById(employeeIdToBeFind))
                .thenReturn(expectedEmployee);
        Employee actualEmployee = employeeService.findById(employeeIdToBeFind);
        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void getEmployeeByIdFromDataBase_FindWrongId_ShouldReturnNull() {
        int employeeIdToBeFind = 11;
        when(persistenceService.findById(employeeIdToBeFind))
                .thenReturn(null);
        Employee actualEmployee = employeeService.findById(employeeIdToBeFind);
        assertNull(actualEmployee);
    }

    @Test
    void getEmployeeById_FindRightId_ShouldReturnEmployeeDTO() {
        //input data
        int employeeIdToBeFind = 2;
        //expected
        Employee expectedEmployee = getEmployeeFromTestListById(employeeIdToBeFind);
        EmployeeDTO expectedEmployeeDTO = employeeToEmployeeDTO(expectedEmployee);

        //mock
        when(persistenceService.findById(employeeIdToBeFind)).thenReturn(expectedEmployee);
        when(employeeMapper.EmployeeToEmployeeDto(expectedEmployee)).thenReturn(expectedEmployeeDTO);

        //actual
        EmployeeDTO actualEmployeeDTO = employeeService.getEmployeeById(employeeIdToBeFind);
        assertEquals(expectedEmployeeDTO, actualEmployeeDTO);
    }

    @Test
    void getAllEmployees_ShouldReTurnEmployeeDTOList() {
        List<EmployeeDTO> expectedEmployeeDTOList = new ArrayList<>();
        expectedEmployeeDTOList.add(employeeToEmployeeDTO(employee1));
        expectedEmployeeDTOList.add(employeeToEmployeeDTO(employee2));
        when(persistenceService.findAll()).thenReturn(employeeList);
        when(employeeMapper.EmployeesToEmployeeDtos(employeeList)).thenReturn(expectedEmployeeDTOList);
        List<EmployeeDTO> actualEmployeeDTOList = employeeService.getAllEmployees();
        assertEquals(expectedEmployeeDTOList, actualEmployeeDTOList);

    }

    @Test
    void addEmployee_RightEmployeeRequest_ShouldAddEmployeeToDataBase() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("A");
        employeeRequest.setMiddleName("B");
        employeeRequest.setLastName("C");
        employeeRequest.setGender(Gender.FEMALE);
        employeeRequest.setSalary(101010);
        employeeRequest.setDateOfBirth(LocalDate.of(2021, 3, 4));
        employeeRequest.setDepartmentId(2);

        Employee employeeWithOutDepartment = new Employee();
        employeeWithOutDepartment.setFirstName(employeeRequest.getFirstName());
        employeeWithOutDepartment.setMiddleName(employeeRequest.getMiddleName());
        employeeWithOutDepartment.setLastName(employeeRequest.getLastName());
        employeeWithOutDepartment.setGender(employeeRequest.getGender());
        employeeWithOutDepartment.setSalary(employeeRequest.getSalary());
        employeeWithOutDepartment.setDateOfBirth(employeeRequest.getDateOfBirth());

        Department department = new Department();
        department.setId(employeeRequest.getDepartmentId());

        when(employeeMapper.EmployeeRequestToEmployee(employeeRequest)).thenReturn(employeeWithOutDepartment);
        when(departmentService.findById(employeeRequest.getDepartmentId())).thenReturn(department);

        Employee expectedEmployee = employeeWithOutDepartment;
        expectedEmployee.setDepartment(departmentService.findById(employeeRequest.getDepartmentId()));
        EmployeeDTO expectedEmployeeDTO = employeeToEmployeeDTO(expectedEmployee);

        when(persistenceService.save(expectedEmployee)).thenReturn(expectedEmployee);
        when(employeeMapper.EmployeeToEmployeeDto(expectedEmployee)).thenReturn(expectedEmployeeDTO);
        EmployeeDTO actualEmployeeDTO = employeeService.addEmployee(employeeRequest);

        assertEquals(expectedEmployeeDTO, actualEmployeeDTO);
        verify(persistenceService).save(expectedEmployee);

    }

    @Test
    void deleteEmployeeById_RightEmployeeId_ShouldDeleteEmployee() {
        Employee employeeToBeDelete = employeeList.get(0);
//        when(persistenceService.findById(anyInt())).thenReturn(employeeToBeDelete);
        employeeService.remove(employeeToBeDelete.getId());
        verify(persistenceService).remove(employeeToBeDelete.getId());
    }

    @Test
    @Disabled("need to test this case in persistenceServiceImpl")
    void deleteEmployeeById_WrongEmployeeId_ShouldNotDeleteEmployee() {
        when(persistenceService.findById(anyInt())).thenReturn(null);
        employeeService.remove(0);
        verify(entityManager, never()).remove(any(Employee.class));
    }

    @Test
    void updateEmployeeById_RightEmployeeId_ShouldUpdateEmployee() {
        Employee employeeBeforeUpdate = employeeList.get(0);

        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName("A");
        employeeRequest.setMiddleName("B");
        employeeRequest.setLastName("C");
        employeeRequest.setGender(Gender.FEMALE);
        employeeRequest.setSalary(10);
        employeeRequest.setDateOfBirth(LocalDate.of(2030, 1, 2));
        employeeRequest.setDepartmentId(2);

        Department department = new Department();
        department.setId(10);

        when(persistenceService.findById(anyInt())).thenReturn(employeeBeforeUpdate);
        when(departmentService.findById(employeeRequest.getDepartmentId())).thenReturn(department);
        Employee updatedEmployee = employeeBeforeUpdate;
        updatedEmployee.setFirstName(employeeRequest.getFirstName());
        updatedEmployee.setMiddleName(employeeRequest.getMiddleName());
        updatedEmployee.setLastName(employeeRequest.getLastName());
        updatedEmployee.setGender(employeeRequest.getGender());
        updatedEmployee.setSalary(employeeRequest.getSalary());
        updatedEmployee.setDateOfBirth(employeeRequest.getDateOfBirth());
        updatedEmployee.setDepartment(departmentService.findById(employeeRequest.getDepartmentId()));

        EmployeeDTO expectedUpdatedEmployeeDTO = employeeToEmployeeDTO(updatedEmployee);
        when(persistenceService.update(updatedEmployee)).thenReturn(updatedEmployee);
        when(employeeMapper.EmployeeToEmployeeDto(updatedEmployee)).thenReturn(expectedUpdatedEmployeeDTO);

        EmployeeDTO actualUpdatedEmployeeDTO = employeeService.updateEmployeeById(employeeBeforeUpdate.getId(), employeeRequest);
        assertEquals(expectedUpdatedEmployeeDTO, actualUpdatedEmployeeDTO);
        verify(persistenceService).update(updatedEmployee);

    }

    @Test
    void updateEmployeeById_WrongEmployeeId_ShouldThrowWebApplicationException() {
        int employeeId = 0;
        when(persistenceService.findById(anyInt())).thenReturn(null);

        try {
            employeeService.updateEmployeeById(employeeId,any(EmployeeRequest.class));
            fail("expect to throw WebApplicationException");
        } catch (WebApplicationException e){
            Response response = e.getResponse();
            assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
            assertEquals("Không có Employee với Id: "+employeeId,response.getEntity().toString());
            verify(persistenceService,never()).update(any(Employee.class));
        }

    }

    private EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        EmployeeDTO expectedEmployeeDTO = new EmployeeDTO();
        expectedEmployeeDTO.setFullName(employee.getFirstName() + " " + employee.getMiddleName() + " " + employee.getLastName());
        expectedEmployeeDTO.setDateOfBirth(employee.getDateOfBirth());
        expectedEmployeeDTO.setSalary(employee.getSalary());
        expectedEmployeeDTO.setGender(employee.getGender());
        expectedEmployeeDTO.setId(employee.getId());
        expectedEmployeeDTO.setDepartmentId(employee.getDepartment().getId());
        return expectedEmployeeDTO;
    }

    private Employee getEmployeeFromTestListById(int employeeIdToBeFind) {
        return employeeList.stream()
                .filter(e -> e.getId() == employeeIdToBeFind)
                .findFirst()
                .get();
    }

}