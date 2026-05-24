package com.example.techcorp.domain;

public abstract class Employee {

    private String name;
    private int skill;
    private int salary;

    public Employee(String name, int skill, int salary) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (skill < 1 || skill > 10) {
            throw new IllegalArgumentException("Skill must be between 1 and 10");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        this.name = name;
        this.skill = skill;
        this.salary = salary;
    }

    public abstract int work();

    public double getEfficiency() {
    return (double) skill / salary;
}

    public String getName() {
        return name;
    }

    public int getSkill() {
        return skill;
    }

    public int getSalary() {
        return salary;
    }
}