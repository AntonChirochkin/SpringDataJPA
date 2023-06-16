package ru.skypro.springdatajpa.exeption;

import ru.skypro.springdatajpa.department.Employee;

public class EmployeeNotValidExeption extends RuntimeException{
    private final Employee employee;

    public EmployeeNotValidExeption(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
