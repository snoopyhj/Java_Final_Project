package com.example.techcorp.domain;

public class Developer extends Employee {

    public Developer(String name, int skill, int salary) {
        super(name, skill, salary);
    }

    @Override
    public int work() {
        return getSkill() * 2;
    }
}