package com.example.techcorp.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.techcorp.exception.ProjectAlreadyFinishedException;

public class Project {
    private String name;
    private int progress;
    private int requiredWork;
    private ProjectStatus status;
    private List<Employee> employees;
    private int reward = 20000;
    private ProjectType type;

public Project(String name, int requiredWork, ProjectType type) {
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
    this.type = type;


        switch (type) {
        case MOBILE_APP:
            reward = 20000;
            break;
        case WEB_APP:
            reward = 15000;
            break;
        case AI_SYSTEM:
            reward = 40000;
            break;
        case GAME_DEV:
            reward = 30000;
            break;
    }
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

    public boolean start() {
        if (status == ProjectStatus.PLANNED) {
            status = ProjectStatus.IN_PROGRESS;
            return true;
    }

        return false;
    }

public void workOneTurn() {
    if (status != ProjectStatus.IN_PROGRESS) {
        System.out.println("Project is not in progress.");
        return;
    }

    if (isFinished()) {
        throw new ProjectAlreadyFinishedException(
            "Project is already finished!"
        );
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

    public int getReward() {
    return reward;
    }

    public ProjectType getType() {
    return type;
}

public void removeEmployee(Employee employee) {
    employees.remove(employee);
}

}
