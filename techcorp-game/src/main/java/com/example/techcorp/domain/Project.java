package com.example.techcorp.domain;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private int progress;
    private int requiredWork;
    private ProjectStatus status;
    private List<Employee> employees;

public Project(String name, int requiredWork) {
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Project name cannot be empty");
    }
    if (requiredWork <= 0) {
        throw new IllegalArgumentException("Required work must be positive");
    }

    this.name = name;
    this.requiredWork = requiredWork;
    this.progress = 0;
    this.status = ProjectStatus.PLANNED;
    this.employees = new ArrayList<>();
}

    public void addEmployee(Employee employee) {
    if (employee == null) {
        return;
    }

    if (employees.contains(employee)) {
        return;
    }

    employees.add(employee);
}

public int getRequiredWork() {
    return requiredWork;
}

    public void start() {
        if (status == ProjectStatus.PLANNED) {
            status = ProjectStatus.IN_PROGRESS;
        }
    }

public void workOneTurn() {
    if (status != ProjectStatus.IN_PROGRESS) {
        System.out.println("Project is not in progress.");
        return;
    }

    if (employees.isEmpty()) {
        System.out.println("No employees assigned.");
        return;
    }

    int totalWork = 0;

    for (Employee e : employees) {
        totalWork += e.work();
    }

    progress += totalWork;

    if (progress >= requiredWork) {
        progress = requiredWork;
        status = ProjectStatus.FINISHED;
    }
}

    public boolean isFinished() {
        return progress >= requiredWork;
    }

    public String getName() {
        return name;
    }

    public int getProgress() {
        return progress;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
