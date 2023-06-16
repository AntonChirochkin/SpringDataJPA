package ru.skypro.springdatajpa.dto;


public class EmployeeDto {
    private int id;
    private String name;
    private int salary;

    public EmployeeDto(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public EmployeeDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}