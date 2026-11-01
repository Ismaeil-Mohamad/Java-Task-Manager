package com.codewithsimo.service;

import com.codewithsimo.enums.Priority;
import com.codewithsimo.enums.TaskStatus;
import com.codewithsimo.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final List<Task> tasks;
    private int nextTaskId;

    public TaskService() {
        this.tasks = new ArrayList<>();
        this.nextTaskId = 1;
    }

    public Task addTask(String title, String description, Priority priority, LocalDate dueDate) {
        Task task = new Task(nextTaskId, title, description, priority, dueDate);
        tasks.add(task);
        nextTaskId++;
        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Optional<Task> getTaskById(int id) {

        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
    }

    public boolean updateTask(
            int id,
            String title,
            String description,
            Priority priority,
            LocalDate dueDate
    ) {
        Optional<Task> optionalTask = getTaskById(id);
        if (optionalTask.isEmpty()) {
            return false;
        }
        Task task = optionalTask.get();
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setDueDate(dueDate);

        return true;

    }

    public boolean deleteTask(int id) {

        Optional<Task> optionalTask = getTaskById(id);

        if (optionalTask.isEmpty()) {
            return false;
        }

        tasks.remove(optionalTask.get());

        return true;
    }

    public boolean completeTask(int id) {

        Optional<Task> optionalTask = getTaskById(id);

        if (optionalTask.isEmpty()) {
            return false;
        }

        optionalTask.get().setStatus(TaskStatus.DONE);

        return true;
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    public List<Task> getTasksByPriority(Priority priority) {

        return tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .toList();
    }


}


