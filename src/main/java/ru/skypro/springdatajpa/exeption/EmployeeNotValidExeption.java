package ru.skypro.springdatajpa.exeption;

import ru.skypro.springdatajpa.department.Employee;
import ru.skypro.springdatajpa.dto.EmployeeDto;

public class EmployeeNotValidExeption extends RuntimeException{
    private final EmployeeDto employee;

    public EmployeeNotValidExeption(EmployeeDto employee) {
        this.employee = employee;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }
}
