package ru.skypro.springdatajpa.service;

import org.springframework.stereotype.Component;
import ru.skypro.springdatajpa.department.Employee;
import ru.skypro.springdatajpa.department.Position;
import ru.skypro.springdatajpa.dto.EmployeeDto;

import java.util.Optional;

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

        employeeDto.setPosition(
                Optional.ofNullable(employee.getPosition())
                .map(Position::getPosition)
                .orElse(null)
        );
        return employeeDto;
    }
}