package ru.skypro.springdatajpa.controller;


import org.springframework.web.bind.annotation.*;
import ru.skypro.springdatajpa.dto.EmployeeDto;
import ru.skypro.springdatajpa.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/salary/sum")
    public int getSumOfSalaries() {
        return employeeService.getSumOfSalaries();
    }
    @GetMapping("/salary/min")
    public EmployeeDto getEmployeeWithMinSalary() {
        return employeeService.getEmployeeWithMinSalary();
    }
    @GetMapping("/salary/max")
    public EmployeeDto getEmployeeWithMaxSalary() {
        return employeeService.getEmployeeWithMaxSalary();
    }

    @GetMapping("/high-salary")
    public List<EmployeeDto> getEmployeeWithSalaryHigherThanAverage() {
        return employeeService.getEmployeeWithSalaryHigherThanAverage();
    }

    // Создание множества новых сотрудников
    @PostMapping
    public List<EmployeeDto> createManyEmployee(@RequestBody List<EmployeeDto> employeeList){
        return employeeService.createManyEmployee(employeeList);
    }
    // Редактирование сотрудника с указанным id;
    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody EmployeeDto employee){
        employeeService.update(id, employee);
    }
    //Возвращение информации о сотруднике с переданным id;
    @GetMapping("/{id}")
    public EmployeeDto get(@PathVariable int id) {
        return employeeService.get(id);
    }
    //Удаление сотрудника с переданным id.
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        employeeService.delete(id);
    }
    //Метод возвращения всех сотрудников, зарплата которых выше переданного параметра salary.
    @GetMapping("/salaryHigherThan")
    public List<EmployeeDto> getFindEmployeeSalaryHigherThan(@RequestParam int salary) {
        return employeeService.getFindEmployeeSalaryHigherThan(salary);
    }
    // Возвращение информации о сотрудниках с самой высокой зарплатой в фирме;
    @GetMapping("/withHighestSalary")
    public List<EmployeeDto> getEmployeesWithHighestSalary() {
        return employeeService.getEmployeesWithHighestSalary();
    }
    @GetMapping
    public List<EmployeeDto> getEmployees(@RequestParam(required = false) String position) {
        return employeeService.getEmployees(
                Optional.ofNullable(position)
                        .filter(pos -> !pos.isEmpty())
                        .orElse(null)
        );
    }
}