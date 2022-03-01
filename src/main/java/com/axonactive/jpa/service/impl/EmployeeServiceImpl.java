package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.controller.request.EmployeeRequest;
import com.axonactive.jpa.entities.Department;
import com.axonactive.jpa.entities.Employee;
import com.axonactive.jpa.enumerate.Gender;
import com.axonactive.jpa.service.DepartmentService;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.dto.EmployeeDTO;
import com.axonactive.jpa.service.dto.EmployeeGroupByDepartmentDTO;
import com.axonactive.jpa.service.mapper.EmployeeMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequestScoped
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    public static final int YEARS_TO_SUBTRACT = 21;
    @PersistenceContext(unitName = "jpa")
    EntityManager em;

    @Inject
    EmployeeMapper employeeMapper;



    @Inject
    DepartmentService departmentService;

    @Override
    public List<EmployeeDTO> getAllEmployeeByDepartment(int departmentId) {
        TypedQuery<Employee> namedQuery = em.createNamedQuery(Employee.GET_ALL, Employee.class);
        namedQuery.setParameter("departmentId", departmentId);
        List<Employee> employeeList = namedQuery.getResultList();
        return employeeMapper.EmployeesToEmployeeDtos(employeeList);
    }

    @Override
    public EmployeeDTO getEmployeeByDeptIdAndEmployeeId(int departmentId, int employeeId) {
        return employeeMapper.EmployeeToEmployeeDto(getEmployeeByDeptIdAndEmployeeIdHelper(departmentId, employeeId));
    }

    private Employee getEmployeeByDeptIdAndEmployeeIdHelper(int departmentId, int employeeId) {
        TypedQuery<Employee> namedQuery = em.createNamedQuery(Employee.GET_EMPLOYEE_BY_DEPTID_AND_EMPLOYEEID, Employee.class);
        namedQuery.setParameter("departmentId", departmentId);
        namedQuery.setParameter("employeeId", employeeId);
        return namedQuery.getSingleResult();
    }

    public Employee getEmployeeById(int employeeId){
        TypedQuery<Employee> namedQuery = em.createNamedQuery(Employee.GET_EMPLOYEE_BY_ID, Employee.class);
        namedQuery.setParameter("employeeId",employeeId);
        return namedQuery.getSingleResult();

    }

    @Override
    public EmployeeDTO addEmployee(int departmentId, EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.EmployeeRequestToEmployee(employeeRequest);
        employee.setDepartment(departmentService.getDepartmentById(departmentId));
        em.persist(employee);
        return employeeMapper.EmployeeToEmployeeDto(employee);
    }

    @Override
    public void deleteEmployee(int departmentId, int employeeId) {
        Employee employee = getEmployeeByDeptIdAndEmployeeIdHelper(departmentId, employeeId);
        if (Objects.nonNull(employee)) {
            em.remove(employee);
        }
    }

    @Override
    public EmployeeDTO updateEmployee(int departmentId, int employeeId, EmployeeRequest employeeRequest) {
        Employee employee = getEmployeeByDeptIdAndEmployeeIdHelper(departmentId, employeeId);
        Employee newEmployee = employeeMapper.EmployeeRequestToEmployee(employeeRequest);
        newEmployee.setId(employee.getId());
        newEmployee.setDepartment(employee.getDepartment());
        em.merge(newEmployee);
        return employeeMapper.EmployeeToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeGroupByDepartmentDTO> getAllEmployeeGroupByDepartment() {
        List<EmployeeGroupByDepartmentDTO> employeeGroupByDepartmentDTOList = new ArrayList<>();
        List<Employee> employeeList = getEmployeeList();
        Map<Department, List<Employee>> employeeGroupByDepartment = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment));
        employeeGroupByDepartment.forEach((d,e)-> employeeGroupByDepartmentDTOList.add(
                EmployeeGroupByDepartmentDTO.builder()
                .departmentName(d.getName())
                .startDate(d.getStartDate())
                .numOfEmployee(e.size())
                .numOfFemale(e.stream().filter(employee->employee.getGender()== Gender.FEMALE).count())
                .numOfMale(e.stream().filter(employee->employee.getGender()== Gender.MALE).count())
                .numOfU21(e.stream().filter(employee -> employee.getDateOfBirth().isAfter(LocalDate.now().minusYears(YEARS_TO_SUBTRACT))).count())
                .build()));
        return employeeGroupByDepartmentDTOList;
    }

    private List<Employee> getEmployeeList() {
        return em.createQuery("from Employee", Employee.class).getResultList();
    }

    @Override
    public List<EmployeeDTO> getEmployeeByBirthMonth(int month) {
        return employeeMapper.EmployeesToEmployeeDtos(getEmployeeList().stream()
                .filter(employee -> employee.getDateOfBirth().getMonthValue()==month).collect(Collectors.toList()));
    }


}
