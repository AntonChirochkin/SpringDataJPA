package ru.skypro.springdatajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.springdatajpa.department.Employee;
import ru.skypro.springdatajpa.dto.EmployeeDto;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT SUM(e.salary) FROM Employee e")
    int getSumOfSalaries();

    @Query("SELECT AVG(e.salary) FROM Employee e")
    double getAverageOfSalaries();

    @Query("SELECT new ru.skypro.springdatajpa.dto.EmployeeDto(e.id, e.name, e.salary) FROM Employee e WHERE e.salary = (SELECT MIN (e.salary) FROM Employee e)")
    Page<EmployeeDto> getEmployeeWithMinSalary(Pageable pageable);

    @Query("SELECT new ru.skypro.springdatajpa.dto.EmployeeDto(e.id, e.name, e.salary) from Employee e WHERE e.salary = (SELECT MAX (e.salary) FROM Employee e)")
    Page<EmployeeDto> getEmployeeWithMaxSalary(Pageable pageable);

    @Query("SELECT new ru.skypro.springdatajpa.dto.EmployeeDto(e.id, e.name, e.salary) from Employee e WHERE e.salary > :salary")
    List<EmployeeDto> findEmployeesBySalaryIsGreaterThen(double salary);
}
