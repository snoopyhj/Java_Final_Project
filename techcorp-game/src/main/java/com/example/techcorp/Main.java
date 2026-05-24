package com.example.techcorp;

import com.example.techcorp.domain.*;
import com.example.techcorp.ui.ConsoleUI;
import com.example.techcorp.engine.GameEngine;

public class Main {
    public static void main(String[] args) {
        Company company = new Company("TechCorp", 80000);

        // Employees
        company.hire(new Developer("Anna", 9, 8000));
        company.hire(new Developer("Marek", 7, 7000));
        company.hire(new Developer("Julia", 8, 7500));
        company.hire(new Tester("Piotr", 6, 6500));
        company.hire(new Tester("Kasia", 5, 6000));
        company.hire(new Manager("Ewa", 7, 9000));
        company.hire(new Manager("Tomasz", 6, 8500));

        // Projects
        Project p1 = new Project("Mobile Banking App", 40, ProjectType.MOBILE_APP);
        Project p2 = new Project("E-commerce Website", 35, ProjectType.WEB_APP);
        Project p3 = new Project("AI Chatbot", 60, ProjectType.AI_SYSTEM);

        company.addProject(p1);
        company.addProject(p2);
        company.addProject(p3);

        // Start with first project (for now)
        ConsoleUI ui = new ConsoleUI();
        GameEngine engine = new GameEngine(company, p1, ui);

        engine.start();
    }

}
