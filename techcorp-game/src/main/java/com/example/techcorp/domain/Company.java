package com.example.techcorp.domain;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private double cash;
    private List<Employee> employees;
    private List<Project> projects = new ArrayList<>();

public boolean paySalariesFor(List<Employee> workers) {
    int totalSalaries = 0;

    for (Employee e : workers) {
        totalSalaries += e.getSalary();
    }

    if (cash >= totalSalaries) {
        cash -= totalSalaries;
        System.out.println("Paid project salaries: " + totalSalaries);
        System.out.println("Cash after salary payment: " + cash);
        return true;
    } else {
        System.out.println("Not enough cash to pay project workers!");
        return false;
    }
}

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

    public void paySalaries() {
    int totalSalaries = 0;

    for (Employee e : employees) {
        totalSalaries += e.getSalary();
    }

    if (cash >= totalSalaries) {
        cash -= totalSalaries;
        System.out.println("Paid salaries: " + totalSalaries);
    } else {
        System.out.println("Not enough cash to pay salaries!");
        // Optional: penalties
    }

}

public boolean isBankrupt() {
    return cash <= 0;
}

public void addCash(double amount) {
    if (amount > 0) {
        cash += amount;
    }
}

public void addProject(Project project) {
    if (project != null) {
        projects.add(project);
    }
}

public List<Project> getProjects() {
    return projects;
}

}
