package com.codewithsimo.ui;

import com.codewithsimo.enums.Priority;
import com.codewithsimo.enums.TaskStatus;
import com.codewithsimo.service.TaskService;
import com.codewithsimo.model.Task;
import com.codewithsimo.service.TaskService;
import com.codewithsimo.util.TaskValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.time.format.DateTimeParseException;


public class TaskMenu {
    private final TaskService taskService;
    private final Scanner scanner;

    public TaskMenu(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n========================================");
            System.out.println("              TASK MANAGER");
            System.out.println("========================================");

            System.out.println("1.  Add Task");
            System.out.println("2.  Show All Tasks");
            System.out.println("3.  Find Task By ID");
            System.out.println("4.  Update Task");
            System.out.println("5.  Delete Task");
            System.out.println("6.  Complete Task");
            System.out.println("7.  Filter Tasks By Status");
            System.out.println("8.  Filter Tasks By Priority");
            System.out.println("9.  Search Tasks By Title");
            System.out.println("10. Sort Tasks By Priority");
            System.out.println("11. Sort Tasks By Due Date");
            System.out.println("12. Show Task Summary");
            System.out.println("0.  Exit");

            System.out.println("========================================");

            int choice = readInt("Enter your chioce: ");
            if (choice == 0) {
                running = false;
                System.out.println("Thank you for using Task Manager!");
            } else {
                handleUserChoice(choice);
            }


        }
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                addTaskMenu();
                break;
            case 2:
                displayTasks();
                break;
            case 3:
                findTaskMenu();
                break;
            case 4:
                updateTaskMenu();
                break;
            case 5:
                deleteTaskMenu();
                break;
            case 6:
                completeTaskMenu();
                break;
            case 7:
                filterTasksByStatusMenu();
                break;
            case 8:
                filterTasksByPriorityMenu();
                break;
            case 9:
                searchTasksMenu();
                break;
            case 10:
                sortTasksByPriorityMenu();
                break;
            case 11:
                sortTasksByDueDateMenu();
                break;
            case 12:
                showStatisticsMenu();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");


        }
    }


    private void addTaskMenu() {
        System.out.println("\n========== ADD TASK ==========");
        String title = readTitle();
        String description = readDescription();
        Priority priority = readPriority();
        LocalDate dueDate = readDueDate();
        Task task = taskService.addTask(
                title,
                description,
                priority,
                dueDate
        );
        System.out.println("\nTask added successfully!");
        System.out.println(task);


    }

    private void displayTasks() {
        System.out.println("\n========== ALL TASKS ==========");
        List<Task> tasks = taskService.getAllTasks();
        displayTaskList(tasks);
    }

    private void findTaskMenu() {
        System.out.println("\n========== FIND TASK ==========");
        int id = readInt("Enter task ID:");
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            System.out.println(task.get());
        } else {
            System.out.println("Task not found.");
        }

    }


    private void updateTaskMenu() {

        System.out.println("\n========== UPDATE TASK ==========");

        int id = readInt("Enter task ID: ");

        Optional<Task> existingTask = taskService.getTaskById(id);

        if (existingTask.isEmpty()) {

            System.out.println("Task not found.");
            return;
        }

        System.out.println("Enter the new information:");

        String title = readTitle();

        String description = readDescription();

        Priority priority = readPriority();

        LocalDate dueDate = readDueDate();

        boolean updated = taskService.updateTask(
                id,
                title,
                description,
                priority,
                dueDate
        );

        if (updated) {

            System.out.println("Task updated successfully!");

        } else {

            System.out.println("Could not update task.");
        }
    }


    private void deleteTaskMenu() {
        System.out.println("\n========== DELETE TASK ==========");

        int id = readInt("Enter task ID: ");

        boolean deleted = taskService.deleteTask(id);

        if (deleted) {

            System.out.println("Task deleted successfully!");

        } else {

            System.out.println("Task not found.");
        }

    }

    private void completeTaskMenu() {
        System.out.println("\n========== COMPLETE TASK ==========");
        int id = readInt("Enter task ID:");
        boolean completed = taskService.completeTask(id);
        if (completed) {
            System.out.println("Task marked as DONE!");
        } else {
            System.out.println("Task not found.");

        }

    }

    private void filterTasksByStatusMenu() {
        System.out.println("\n========== FILTER BY STATUS ==========");
        TaskStatus[] statuses = TaskStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);

        }
        int choice = readInt("Choose status: ");
        if (choice < 1 || choice > statuses.length) {
            System.out.println("Invalid status choice.");
            return;
        }
        TaskStatus selectedStatus = statuses[choice - 1];
        List<Task> tasks =
                taskService.getTasksByStatus(selectedStatus);
        displayTaskList(tasks);


    }

    private void filterTasksByPriorityMenu() {

        System.out.println("\n========== FILTER BY PRIORITY ==========");

        Priority[] priorities = Priority.values();

        for (int i = 0; i < priorities.length; i++) {

            System.out.println((i + 1) + ". " + priorities[i]);
        }

        int choice = readInt("Choose priority: ");

        if (choice < 1 || choice > priorities.length) {

            System.out.println("Invalid priority choice.");
            return;
        }

        Priority selectedPriority = priorities[choice - 1];

        List<Task> tasks =
                taskService.getTasksByPriority(selectedPriority);

        displayTaskList(tasks);
    }

    private void searchTasksMenu() {
        System.out.println("\n========== SEARCH TASKS ==========");
        String title = scanner.nextLine().trim();
        List<Task> tasks = taskService.searchTasksByTitle(title);
        displayTaskList(tasks);

    }


    private void sortTasksByPriorityMenu() {

        System.out.println("\n========== SORT BY PRIORITY ==========");

        List<Task> tasks = taskService.sortTasksByPriority();

        displayTaskList(tasks);
    }

    private void sortTasksByDueDateMenu() {

        System.out.println("\n========== SORT BY DUE DATE ==========");

        List<Task> tasks = taskService.sortTasksByDueDate();

        displayTaskList(tasks);
    }

    private void showStatisticsMenu() {

        System.out.println("\n========== TASK STATISTICS ==========");

        Map<TaskStatus, Long> statistics =
                taskService.getTaskStatistics();

        for (Map.Entry<TaskStatus, Long> entry : statistics.entrySet()) {

            System.out.println(
                    entry.getKey() + ": " + entry.getValue()
            );
        }

        System.out.println(
                "Total Tasks: " + taskService.getAllTasks().size()
        );
    }

    private void displayTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;

        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }


    private int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readTitle() {
        while (true) {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            if (TaskValidator.isValidTitle(title)) {
                return title.trim();
            }
            System.out.println("Title cannot be empty.");


        }

    }

    private String readDescription() {
        while (true) {
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            if (TaskValidator.isValidDescription(description)) {
                return description.trim();
            }
            System.out.println("Description cannot be empty.");

        }

    }


    private Priority readPriority() {
        Priority[] priorities = Priority.values();

        while (true) {

            System.out.println("\nSelect priority:");

            for (int i = 0; i < priorities.length; i++) {

                System.out.println((i + 1) + ". " + priorities[i]);
            }

            int choice = readInt("Enter priority number: ");

            if (choice >= 1 && choice <= priorities.length) {

                return priorities[choice - 1];
            }

            System.out.println("Invalid priority. Please try again.");
        }

    }

    private LocalDate readDueDate() {

        while (true) {

            System.out.print("Enter due date (YYYY-MM-DD): ");

            String input = scanner.nextLine().trim();

            try {

                LocalDate dueDate = LocalDate.parse(input);

                if (TaskValidator.isValidDueDate(dueDate)) {

                    return dueDate;
                }

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date format. Example: 2026-11-10"
                );
            }
        }
    }

}

