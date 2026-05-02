package com.example.techcorp.domain;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private double cash;
    private List<Employee> employees;

public Company(String name, double cash) {
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Company name cannot be empty");
    }
    if (cash < 0) {
        throw new IllegalArgumentException("Cash cannot be negative");
    }

    this.name = name;
    this.cash = cash;
    this.employees = new ArrayList<>();
}

    public void hire(Employee employee) {
    if (employee != null) {
        employees.add(employee);
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }

    public double getCash() {
        return cash;
    }
}
