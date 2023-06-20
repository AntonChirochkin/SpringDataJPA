package ru.skypro.springdatajpa.service;


import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.skypro.springdatajpa.department.Employee;
import ru.skypro.springdatajpa.dto.EmployeeDto;
import ru.skypro.springdatajpa.exeption.EmployeeNotFoundExeption;
import ru.skypro.springdatajpa.exeption.EmployeeNotValidExeption;
import ru.skypro.springdatajpa.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @PostConstruct
    public void init() {
        employeeRepository.deleteAll();
        employeeRepository.saveAll(
                List.of(
                        new Employee("Катя", 90_000),
                        new Employee("Дима", 102_000),
                        new Employee("Олег", 80_000),
                        new Employee("Вика", 165_000),
                        new Employee("Женя", 175_000)
                )
        );
    }

    public int getSumOfSalaries() {
        return employeeRepository. getSumOfSalaries();
    }

    public EmployeeDto getEmployeeWithMinSalary() {
        Page<EmployeeDto> page = employeeRepository.getEmployeeWithMinSalary(PageRequest.of(0, 1));
        if (page.isEmpty()) {
            return null;
        }
        return page.getContent().get(0);
    }

    public EmployeeDto getEmployeeWithMaxSalary() {
        List<EmployeeDto> employeeWithMaxSalary = getEmployeesWithHighestSalary();
        if (employeeWithMaxSalary.isEmpty()) {
            return null;
        }
        return employeeWithMaxSalary.get(0);
    }

    public List<EmployeeDto> getEmployeeWithSalaryHigherThanAverage() {
        double average = employeeRepository.getAverageOfSalaries();
        return getFindEmployeeSalaryHigherThan(average);
    }
    //  в данном методе сделана валидация (если у сотрудника, которого мы хотим добавить оплата <=0 или имя равно null или не указано, тогда кидается исключение  )
    public List<EmployeeDto> createManyEmployee(List<EmployeeDto> employeeList) {
        Optional<EmployeeDto> incorrectEmployee = employeeList.stream()
                .filter(employee -> employee.getSalary() <= 0 ||
                        employee.getName() == null || employee.getName().isEmpty())
                .findFirst();

        if(incorrectEmployee.isPresent()) {
            throw new EmployeeNotValidExeption(incorrectEmployee.get());
        }
        // Тот кто отправляет запрос на создание id не должен управлять id т.е. менять
        return employeeRepository.saveAll(employeeList.stream()
                        .map(employeeMapper::toEntity)
                        .collect(Collectors.toList()))
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());

    }

    public void update(int id, EmployeeDto employee) {
        Employee oldEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundExeption(id));
        oldEmployee.setSalary(employee.getSalary());
        oldEmployee.setName(employee.getName());
        employeeRepository.save(oldEmployee);
    }

    public EmployeeDto get(int id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new EmployeeNotFoundExeption(id));
    }

    public void delete(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundExeption(id));
        employeeRepository.delete(employee);
    }

    public List<EmployeeDto> getFindEmployeeSalaryHigherThan(double salary) {
        return employeeRepository.findEmployeesBySalaryIsGreaterThen(salary);
    }

    public List<EmployeeDto> getEmployeesWithHighestSalary() {
        return employeeRepository.getEmployeeWithMaxSalary();
    }

    public List<EmployeeDto> getEmployees(@Nullable String position) {
        return Optional.ofNullable(position)
                .map(employeeRepository::findEmployeesByPosition_PositionContainingIgnoreCase)
                .orElseGet(employeeRepository::findAll)
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}
