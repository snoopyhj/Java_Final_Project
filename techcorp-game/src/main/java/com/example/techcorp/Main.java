package com.example.techcorp;

import com.example.techcorp.domain.*;
import com.example.techcorp.ui.ConsoleUI;
import com.example.techcorp.engine.GameEngine;
import com.example.techcorp.domain.Company;
import com.example.techcorp.domain.Project;
import com.example.techcorp.domain.Employee;
import com.example.techcorp.domain.Developer;
import com.example.techcorp.domain.Tester;
import com.example.techcorp.domain.Manager;

public class Main {
    public static void main(String[] args) {
        Company company = new Company("TechCorp", 50000);

        company.hire(new Developer("Anna", 9, 8000));
        company.hire(new Tester("Piotr", 6, 6500));
        company.hire(new Manager("Ewa", 7, 9000));

        Project project = new Project("Mobile App", 30);

        ConsoleUI ui = new ConsoleUI();
        GameEngine engine = new GameEngine(company, project, ui);

        engine.start();
    }
}
