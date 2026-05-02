package com.example.techcorp.domain;

public class Manager extends Employee {

    public Manager(String name, int skill, int salary) {
        super(name, skill, salary);
    }

    @Override
    public int work() {
        return getSkill() + 3;
    }
}