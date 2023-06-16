package ru.skypro.springdatajpa.service;

import org.springframework.stereotype.Component;
import ru.skypro.springdatajpa.department.Employee;
import ru.skypro.springdatajpa.dto.EmployeeDto;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());
        return employee;
    }

    public EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setSalary(employee.getSalary());
        return employeeDto;

    }
}