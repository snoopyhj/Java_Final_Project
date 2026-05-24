package com.example.techcorp.engine;

import com.example.techcorp.domain.Company;
import com.example.techcorp.domain.Project;
import com.example.techcorp.domain.ProjectType;
import com.example.techcorp.domain.Employee;
import com.example.techcorp.ui.ConsoleUI;
import com.example.techcorp.domain.ProjectStatus;

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

        if (company.isBankrupt()) {
        ui.showMessage("Company is bankrupt! Game over.");
        running = false;
        }

        if (running) {
            turn++;
        }
    }
}
private void handleChoice(int choice) {
    switch (choice) {
        case 1:
    if (project.start()) {
        ui.showMessage("Project started.");
    } else {
        ui.showMessage("Only planned projects can be started.");
    }
    break;

        case 2:
            assignEmployee();
            break;

        case 3:
            removeEmployee();
            break;

        case 4:
            workOnProject();
            break;

        case 5:
            project = createProjectFromChoice();
            ui.showMessage("New project selected: " + project.getName());
            break;

        case 6:
            running = false;
            ui.showMessage("Game ended.");
            break;

        default:
            ui.showMessage("Invalid choice.");
    }
}

private void workOnProject() {
    if (project.getStatus() != ProjectStatus.IN_PROGRESS) {
        ui.showMessage("Project must be started before work.");
        return;
    }

    if (project.getEmployees().isEmpty()) {
        ui.showMessage("Assign at least one employee before work.");
        return;
    }

    if (!company.paySalariesFor(project.getEmployees())) {
        ui.showMessage("Work cancelled because salaries could not be paid.");
        return;
    }

    try {
        project.workOneTurn();
        ui.showMessage("Project progressed.");
    } catch (RuntimeException e) {
        ui.showMessage(e.getMessage());
        return;
    }

    if (project.isFinished()) {
        company.addCash(project.getReward());
        ui.showMessage("Project finished! Earned: " + project.getReward());
        ui.showMessage("Choose option 5 to select a new project.");
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

private Project createProjectFromChoice() {
    int choice = ui.chooseProjectType();

    Project newProject;

    switch (choice) {
        case 1:
            newProject = new Project(
                "Mobile Banking App",
                40,
                ProjectType.MOBILE_APP
            );
            break;

        case 2:
            newProject = new Project(
                "E-commerce Website",
                35,
                ProjectType.WEB_APP
            );
            break;

        case 3:
            newProject = new Project(
                "AI Chatbot",
                60,
                ProjectType.AI_SYSTEM
            );
            break;

        case 4:
            newProject = new Project(
                "Indie Game",
                50,
                ProjectType.GAME_DEV
            );
            break;

        default:
            ui.showMessage("Invalid choice. Default selected.");

            newProject = new Project(
                "Simple Website",
                20,
                ProjectType.WEB_APP
            );
    }

    company.addProject(newProject);

    return newProject;
}

    private void removeEmployee() {

    if (project.getEmployees().isEmpty()) {
        ui.showMessage("No employees assigned.");
        return;
    }

    ui.showEmployees(project.getEmployees());

    int index = ui.getChoice();

    if (index < 0 || index >= project.getEmployees().size()) {
        ui.showMessage("Invalid employee.");
        return;
    }

    Employee employee = project.getEmployees().get(index);

    project.removeEmployee(employee);

    ui.showMessage(
        employee.getName() + " removed from project."
    );
}

}