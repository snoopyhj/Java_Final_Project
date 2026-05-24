package com.example.techcorp.ui;

import com.example.techcorp.domain.Company;
import com.example.techcorp.domain.Employee;
import com.example.techcorp.domain.Project;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);

    public void showTurn(int turn) {
        System.out.println("\n=== Turn " + turn + " ===");
    }

    public void showStatus(Company company, Project project) {
    System.out.println("Company: " + company.getName());
    System.out.println("Cash: " + company.getCash());
    System.out.println(
        "Project: " + project.getName()
        + " | Status: " + project.getStatus()
        + " | Progress: " + project.getProgress()
        + "/" + project.getRequiredWork()
    );
System.out.print("Assigned employees: ");

if (project.getEmployees().isEmpty()) {
    System.out.println("None");
} else {

    for (Employee e : project.getEmployees()) {
        System.out.print(e.getName() + " ");
    }

    System.out.println();
}}

public int getChoice() {
    System.out.print("Choose: ");

    if (scanner.hasNextInt()) {
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    } else {
        scanner.nextLine();
        return -1;
    }
}

public void showMainMenu() {
    System.out.println("\nChoose an action:");
    System.out.println("1. Start project");
    System.out.println("2. Assign employee to project");
    System.out.println("3. Remove employee from project");
    System.out.println("4. Work on project");
    System.out.println("5. Choose New Project");
    System.out.println("6. Exit game");
}

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showEmployees(List<Employee> employees) {
        System.out.println("\nEmployees:");
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            System.out.println(
                i + ": " + employee.getName()
                + " | skill: " + employee.getSkill()
                + " | salary: " + employee.getSalary()
            );
        }
    }

    public void showProjects(List<Project> projects) {
    System.out.println("Projects:");
    for (int i = 0; i < projects.size(); i++) {
        Project p = projects.get(i);
        System.out.println(i + ": " + p.getName() + " (" + p.getStatus() + ")");
    }
    }

public int chooseProjectType() {
    System.out.println("\nChoose project type:");
    System.out.println("1. Mobile App");
    System.out.println("2. Web App");
    System.out.println("3. AI System");
    System.out.println("4. Game Dev");

    return getChoice();
}
}
