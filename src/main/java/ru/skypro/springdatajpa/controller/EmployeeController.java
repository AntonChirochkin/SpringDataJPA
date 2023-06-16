package ru.skypro.springdatajpa.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.springdatajpa.department.Employee;
import ru.skypro.springdatajpa.service.EmployeeService;

import java.util.List;

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
    public Employee getEmployeeWithMinSalary() {
        return employeeService.getEmployeeWithMinSalary();
    }
    @GetMapping("/salary/max")
    public Employee getEmployeeWithMaxSalary() {
        return employeeService.getEmployeeWithMaxSalary();
    }

    @GetMapping("/high-salary")
    public List<Employee> getEmployeeWithSalaryHigherThanAverage() {
        return employeeService.getEmployeeWithSalaryHigherThanAverage();
    }

    // Создание множества новых сотрудников
    @PostMapping
    public List<Employee> createManyEmployee(@RequestBody List<Employee> employeeList){
        return employeeService.createManyEmployee(employeeList);
    }
    // Редактирование сотрудника с указанным id;
    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Employee employee){
        employeeService.update(id, employee);
    }
    //Возвращение информации о сотруднике с переданным id;
    @GetMapping("/{id}")
    public Employee get(@PathVariable int id) {
        return employeeService.get(id);
    }
    //Удаление сотрудника с переданным id.
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        employeeService.delete(id);
    }
    //Метод возвращения всех сотрудников, зарплата которых выше переданного параметра salary.
    @GetMapping("/salaryHigherThan")
    public List<Employee> getFindEmployeeSalaryHigherThan(@RequestParam int salary) {
        return employeeService.getFindEmployeeSalaryHigherThan(salary);
    }
}
