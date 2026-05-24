package com.example.techcorp;

import com.example.techcorp.domain.*;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class WebMain {

    private static Company company;
    private static Project project;

    public static void main(String[] args) throws Exception {
        setupGame();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", WebMain::handleHome);
        server.createContext("/start", WebMain::handleStart);
        server.createContext("/assign", WebMain::handleAssign);
        server.createContext("/remove", WebMain::handleRemove);
        server.createContext("/work", WebMain::handleWork);
        server.createContext("/new", WebMain::handleNewProject);
        server.createContext("/report", WebMain::handleReport);

        server.start();

        System.out.println("Web game running on port 8080");
    }

    private static void setupGame() {
        company = new Company("TechCorp", 80000);

        company.hire(new Developer("Anna", 9, 8000));
        company.hire(new Developer("Marek", 7, 7000));
        company.hire(new Tester("Piotr", 6, 6500));
        company.hire(new Manager("Ewa", 7, 9000));

        project = new Project("Mobile Banking App", 40, ProjectType.MOBILE_APP);
    }

    private static void handleHome(HttpExchange exchange) throws IOException {
        send(exchange, page(""));
    }

    private static void handleStart(HttpExchange exchange) throws IOException {
        String msg;

        if (project.start()) {
            msg = "Project started.";
        } else {
            msg = "Only planned projects can be started.";
        }

        send(exchange, page(msg));
    }

    private static void handleAssign(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        int index = getIndex(query);

        String msg;

        if (index < 0 || index >= company.getEmployees().size()) {
            msg = "Invalid employee.";
        } else {
            Employee employee = company.getEmployees().get(index);
            project.addEmployee(employee);
            msg = employee.getName() + " assigned.";
        }

        send(exchange, page(msg));
    }

    private static void handleRemove(HttpExchange exchange) throws IOException {
    String query = exchange.getRequestURI().getQuery();
    int index = getIndex(query);

    String msg;

    if (index < 0 || index >= project.getEmployees().size()) {
        msg = "Invalid employee.";
    } else {
        Employee employee = project.getEmployees().get(index);
        project.removeEmployee(employee);
        msg = employee.getName() + " removed from project.";
    }

    send(exchange, page(msg));
}

    private static void handleWork(HttpExchange exchange) throws IOException {
        String msg;

        if (project.getStatus() != ProjectStatus.IN_PROGRESS) {
            msg = "Project must be started first.";
        } else if (project.getEmployees().isEmpty()) {
            msg = "Assign at least one employee first.";
        } else if (!company.paySalariesFor(project.getEmployees())) {
            msg = "Not enough cash to pay workers.";
        } else {
            project.workOneTurn();
            msg = "Project progressed.";

            if (project.isFinished()) {
                company.addCash(project.getReward());
                msg += " Project finished! Earned: " + project.getReward();
            }
        }

        send(exchange, page(msg));
    }

    private static void handleNewProject(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        int type = getIndex(query);

        switch (type) {
            case 1:
                project = new Project("Mobile Banking App", 40, ProjectType.MOBILE_APP);
                break;
            case 2:
                project = new Project("E-commerce Website", 35, ProjectType.WEB_APP);
                break;
            case 3:
                project = new Project("AI Chatbot", 60, ProjectType.AI_SYSTEM);
                break;
            case 4:
                project = new Project("Indie Game", 50, ProjectType.GAME_DEV);
                break;
            default:
                project = new Project("Simple Website", 20, ProjectType.WEB_APP);
        }

        send(exchange, page("New project selected: " + project.getName()));
    }

    private static int getIndex(String query) {
        if (query == null || !query.startsWith("id=")) {
            return -1;
        }

        try {
            return Integer.parseInt(query.substring(3));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

private static String page(String message) {
    int progressPercent = 0;

    if (project.getRequiredWork() > 0) {
        progressPercent = (project.getProgress() * 100) / project.getRequiredWork();
    }

    StringBuilder html = new StringBuilder();

    html.append("""
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>TechCorp Simulator</title>
            <style>
                body {
                    margin: 0;
                    font-family: Arial, sans-serif;
                    background: #f4f7fb;
                    color: #1f2937;
                }

                .container {
                    max-width: 1100px;
                    margin: 40px auto;
                    padding: 20px;
                }

                .header {
                    background: linear-gradient(135deg, #2563eb, #7c3aed);
                    color: white;
                    padding: 30px;
                    border-radius: 20px;
                    box-shadow: 0 10px 25px rgba(0,0,0,0.15);
                    margin-bottom: 25px;
                }

                .header h1 {
                    margin: 0;
                    font-size: 36px;
                }

                .header p {
                    margin: 8px 0 0;
                    opacity: 0.9;
                }

                .message {
                    background: #ecfdf5;
                    border-left: 6px solid #10b981;
                    padding: 15px 20px;
                    border-radius: 12px;
                    margin-bottom: 20px;
                    font-weight: bold;
                }

                .grid {
                    display: grid;
                    grid-template-columns: 1fr 1fr;
                    gap: 20px;
                }

                .card {
                    background: white;
                    border-radius: 18px;
                    padding: 22px;
                    box-shadow: 0 8px 20px rgba(0,0,0,0.08);
                }

                .card h2 {
                    margin-top: 0;
                    color: #111827;
                }

                .stat {
                    font-size: 18px;
                    margin: 10px 0;
                }

                .cash {
                    font-size: 28px;
                    font-weight: bold;
                    color: #16a34a;
                }

                .badge {
                    display: inline-block;
                    padding: 6px 12px;
                    border-radius: 999px;
                    background: #dbeafe;
                    color: #1d4ed8;
                    font-weight: bold;
                    font-size: 14px;
                }

                .progress-bg {
                    background: #e5e7eb;
                    height: 22px;
                    border-radius: 999px;
                    overflow: hidden;
                    margin: 12px 0;
                }

                .progress-fill {
                    height: 100%;
                    background: linear-gradient(90deg, #22c55e, #3b82f6);
                    width: """ + progressPercent + "%;" + """
                }

                .button {
                    display: inline-block;
                    text-decoration: none;
                    background: #2563eb;
                    color: white;
                    padding: 12px 16px;
                    border-radius: 12px;
                    margin: 6px 6px 6px 0;
                    font-weight: bold;
                }

                .button:hover {
                    background: #1d4ed8;
                }

                .danger {
                    background: #ef4444;
                }

                .danger:hover {
                    background: #dc2626;
                }

                .disabled {
    background: #9ca3af;
    cursor: not-allowed;
    opacity: 0.6;
}

.disabled:hover {
    background: #9ca3af;
    transform: none;
    box-shadow: none;
}

                .employee {
                    background: #f9fafb;
                    padding: 12px;
                    border-radius: 12px;
                    margin-bottom: 10px;
                    border: 1px solid #e5e7eb;
                }

                .small {
                    color: #6b7280;
                    font-size: 14px;
                }

                .full {
                    grid-column: 1 / 3;
                }
            </style>
        </head>
        <body>
        <div class="container">
            <div class="header">
                <h1>TechCorp Simulator</h1>
                <p>Manage employees, complete projects, and grow your company.</p>
            </div>
    """);

    if (!message.isEmpty()) {
        html.append("<div class='message'>").append(message).append("</div>");
    }

    html.append("<div class='grid'>");

    html.append("<div class='card'>");
    html.append("<h2>Company</h2>");
    html.append("<p class='small'>Current budget</p>");
    html.append("<div class='cash'>$").append(company.getCash()).append("</div>");
    html.append("</div>");

    html.append("<div class='card'>");
    html.append("<h2>Current Project</h2>");
    html.append("<p class='stat'><b>").append(project.getName()).append("</b></p>");
    html.append("<p>Type: <span class='badge'>").append(project.getType()).append("</span></p>");
    html.append("<p>Status: <span class='badge'>").append(project.getStatus()).append("</span></p>");
    html.append("<p>Reward: $").append(project.getReward()).append("</p>");

    html.append("<div class='progress-bg'>");
    html.append("<div class='progress-fill'></div>");
    html.append("</div>");

    html.append("<p>")
        .append(project.getProgress())
        .append("/")
        .append(project.getRequiredWork())
        .append(" progress (")
        .append(progressPercent)
        .append("%)</p>");
    html.append("</div>");

    html.append("<div class='card full'>");
    html.append("<h2>Actions</h2>");
    html.append("<a class='button' href='/start'>Start Project</a>");
    html.append("<a class='button' href='/work'>Work on Project</a>");
    
    if (project.isFinished()) {
    html.append("<a class='button' href='/report'>Download Profit Report</a>");
} else {
    html.append("<span class='button disabled'>Download Profit Report</span>");
}
    html.append("</div>");

    html.append("<div class='card'>");
    html.append("<h2>Assigned Employees</h2>");

    if (project.getEmployees().isEmpty()) {
        html.append("<p class='small'>No employees assigned yet.</p>");
    } else {
        for (int i = 0; i < project.getEmployees().size(); i++) {
    Employee e = project.getEmployees().get(i);

    html.append("<div class='employee'>");
    html.append("<b>").append(e.getName()).append("</b>");
    html.append("<p class='small'>")
        .append(e.getClass().getSimpleName())
        .append(" | Skill ")
        .append(e.getSkill())
        .append(" | Salary $")
        .append(e.getSalary())
        .append("</p>");

    html.append("<a class='button danger' href='/remove?id=")
        .append(i)
        .append("'>Remove</a>");

    html.append("</div>");
}
    }

    html.append("</div>");

    html.append("<div class='card'>");
    html.append("<h2>Assign Employee</h2>");

    for (int i = 0; i < company.getEmployees().size(); i++) {
        Employee e = company.getEmployees().get(i);

        html.append("<div class='employee'>");
        html.append("<b>").append(e.getName()).append("</b>");
        html.append("<p class='small'>")
            .append(e.getClass().getSimpleName())
            .append(" | Skill ")
            .append(e.getSkill())
            .append(" | Salary $")
            .append(e.getSalary())
            .append("</p>");
        html.append("<a class='button' href='/assign?id=")
            .append(i)
            .append("'>Assign</a>");
        html.append("</div>");
    }

    html.append("</div>");

    html.append("<div class='card full'>");
    html.append("<h2>Choose New Project</h2>");
    html.append("<a class='button' href='/new?id=1'>Mobile App</a>");
    html.append("<a class='button' href='/new?id=2'>Web App</a>");
    html.append("<a class='button' href='/new?id=3'>AI System</a>");
    html.append("<a class='button' href='/new?id=4'>Game Dev</a>");
    html.append("</div>");

    html.append("</div>");
    html.append("</div></body></html>");

    return html.toString();
}

    private static void handleReport(HttpExchange exchange) throws IOException {
    StringBuilder report = new StringBuilder();

    report.append("TechCorp Profit Report\n");
    report.append("======================\n\n");

    report.append("Company: ").append(company.getName()).append("\n");
    report.append("Current Cash: $").append(company.getCash()).append("\n\n");

    report.append("Current Project\n");
    report.append("---------------\n");
    report.append("Name: ").append(project.getName()).append("\n");
    report.append("Type: ").append(project.getType()).append("\n");
    report.append("Status: ").append(project.getStatus()).append("\n");
    report.append("Progress: ")
          .append(project.getProgress())
          .append("/")
          .append(project.getRequiredWork())
          .append("\n");
    report.append("Reward: $").append(project.getReward()).append("\n\n");

    report.append("Assigned Employees\n");
    report.append("------------------\n");

    int totalSalary = 0;

    if (project.getEmployees().isEmpty()) {
        report.append("None\n");
    } else {
        for (Employee e : project.getEmployees()) {
            report.append("- ")
                  .append(e.getName())
                  .append(" | Role: ")
                  .append(e.getClass().getSimpleName())
                  .append(" | Skill: ")
                  .append(e.getSkill())
                  .append(" | Salary: $")
                  .append(e.getSalary())
                  .append(" | Work per turn: ")
                  .append(e.work())
                  .append("\n");

            totalSalary += e.getSalary();
        }
    }

    report.append("\nFinancial Summary\n");
    report.append("-----------------\n");
    report.append("Total assigned salary per work turn: $")
          .append(totalSalary)
          .append("\n");

    int remainingWork = project.getRequiredWork() - project.getProgress();
    int workPerTurn = 0;

    for (Employee e : project.getEmployees()) {
        workPerTurn += e.work();
    }

    report.append("Work per turn: ").append(workPerTurn).append("\n");

    if (workPerTurn > 0) {
        int estimatedTurns = (int) Math.ceil((double) remainingWork / workPerTurn);
        int estimatedCost = estimatedTurns * totalSalary;
        int estimatedProfit = project.getReward() - estimatedCost;

        report.append("Estimated turns to finish: ")
              .append(estimatedTurns)
              .append("\n");

        report.append("Estimated remaining salary cost: $")
              .append(estimatedCost)
              .append("\n");

        report.append("Estimated profit from current project: $")
              .append(estimatedProfit)
              .append("\n");
    } else {
        report.append("Estimated turns to finish: N/A\n");
        report.append("Estimated profit from current project: N/A\n");
    }

    byte[] bytes = report.toString().getBytes(StandardCharsets.UTF_8);

    exchange.getResponseHeaders().add(
        "Content-Type",
        "text/plain; charset=UTF-8"
    );

    exchange.getResponseHeaders().add(
        "Content-Disposition",
        "attachment; filename=\"profit-report.txt\""
    );

    exchange.sendResponseHeaders(200, bytes.length);

    OutputStream os = exchange.getResponseBody();
    os.write(bytes);
    os.close();
}

    private static void send(HttpExchange exchange, String html) throws IOException {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}