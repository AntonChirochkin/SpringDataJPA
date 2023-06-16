package ru.skypro.springdatajpa.service;

import org.springframework.stereotype.Service;
import ru.skypro.springdatajpa.department.Employee;
import ru.skypro.springdatajpa.exeption.EmployeeNotFoundExeption;
import ru.skypro.springdatajpa.exeption.EmployeeNotValidExeption;
import ru.skypro.springdatajpa.repository.EmployeeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public int getSumOfSalaries() {
        return employeeRepository.getAll().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getEmployeeWithMinSalary() {
        return employeeRepository.getAll().stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
    }

    public Employee getEmployeeWithMaxSalary() {
        return employeeRepository.getAll().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
    }

    public List<Employee> getEmployeeWithSalaryHigherThanAverage() {
        double average = employeeRepository.getAll().stream()
                .mapToInt(Employee::getSalary)
                .average()
                .orElse(0D);
        return getFindEmployeeSalaryHigherThan(average);
    }
    //  в данном методе сделана валидация (если у сотрудника, которого мы хотим добавить оплата <=0 или имя равно null или не указано, тогда кидается исключение  )
    public List<Employee> createManyEmployee(List<Employee> employeeList) {
        Optional<Employee> incorrectEmployee = employeeList.stream()
                .filter(employee -> employee.getSalary() <= 0 ||
                        employee.getName() == null || employee.getName().isEmpty())
                .findFirst();

        if(incorrectEmployee.isPresent()) {
            throw new EmployeeNotValidExeption(incorrectEmployee.get());
        }
        // Тот кто отправляет запрос на создание id не должен управлять id т.е. менять
        return employeeList.stream()
                .map(employee -> new Employee(employee.getName(), employee.getSalary()))
                .map(employeeRepository::add)
                .collect(Collectors.toList());
    }

    public void update(int id, Employee employee) {
        Employee oldEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundExeption(id));
        oldEmployee.setSalary(employee.getSalary());
        oldEmployee.setName(employee.getName());
        employeeRepository.update(id, oldEmployee);
    }

    public Employee get(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundExeption(id));
    }

    public void delete(int id) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundExeption(id));
        employeeRepository.delete(id);
    }

    public List<Employee> getFindEmployeeSalaryHigherThan(double salary) {
        return employeeRepository.getAll().stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());
    }
}
