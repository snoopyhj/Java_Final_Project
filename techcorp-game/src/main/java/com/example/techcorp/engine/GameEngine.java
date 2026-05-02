package com.example.techcorp.engine;

import com.example.techcorp.domain.Company;
import com.example.techcorp.domain.Project;
import com.example.techcorp.domain.Employee;
import com.example.techcorp.ui.ConsoleUI;

public class GameEngine {

    private Company company;
    private Project project;
    private ConsoleUI ui;
    private boolean running;
    private int turn;

    public GameEngine(Company company, Project project, ConsoleUI ui) {
        this.company = company;
        this.project = project;
        this.ui = ui;
        this.running = true;
        this.turn = 1;
    }

public void start() {
    while (running) {
        ui.showTurn(turn);
        ui.showStatus(company, project);
        ui.showMainMenu();

        int choice = ui.getChoice();
        handleChoice(choice);

        if (running) {
            turn++;
        }
    }
}

private void handleChoice(int choice) {
    switch (choice) {
        case 1:
            project.start();
            ui.showMessage("Project started.");
            break;

        case 2:
            assignEmployee();
            break;

        case 3:
            project.workOneTurn();
            break;

        case 4:
            running = false;
            ui.showMessage("Game ended.");
            break;

        default:
            ui.showMessage("Invalid choice.");
    }

    if (project.isFinished()) {
        ui.showMessage("Project finished! You win!");
        running = false;
    }
}

    private void assignEmployee() {
        ui.showEmployees(company.getEmployees());
        int empIndex = ui.getChoice();

        if (empIndex < 0 || empIndex >= company.getEmployees().size()) {
            ui.showMessage("Invalid employee");
            return;
        }

        Employee e = company.getEmployees().get(empIndex);
        project.addEmployee(e);

        ui.showMessage(e.getName() + " assigned to " + project.getName());
    }
}