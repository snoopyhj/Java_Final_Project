# Java Programming Fundamentals Final Project  
## TechCorp Business Decision Game  
**Student:** Hyeonji Jo  
**Student ID:** 148697

## Project Description

This project is a turn-based business decision game written in Java.  
The player manages a technology company called **TechCorp**. During the game, the player can start projects, assign employees, remove employees, work on projects, earn rewards, and manage company cash.

The goal of the game is to complete projects successfully while keeping the company financially stable.

## Implemented Features

- Company model
- Employee inheritance hierarchy
- Developer, Tester, and Manager roles
- Different work contribution for each employee type
- Project model
- Project type system
- Project status enum
- Turn-based game loop
- Console menu interaction
- Web UI version
- Employee assignment and removal
- Salary payment system
- Project reward system
- Profit report download feature
- Input validation
- Basic exception handling

## How to Run

```bash
cd techcorp-game
mvn compile
java -cp target/classes com.example.techcorp.Main
