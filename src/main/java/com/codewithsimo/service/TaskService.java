package com.codewithsimo.service;

import com.codewithsimo.enums.Priority;
import com.codewithsimo.enums.TaskStatus;
import com.codewithsimo.model.Task;

import java.time.LocalDate;
import java.util.*;

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

    public List<Task> searchTasksByTitle(String title) {

        return tasks.stream()
                .filter(task ->
                        task.getTitle()
                                .toLowerCase()
                                .contains(title.toLowerCase())
                )
                .toList();
    }

    public List<Task> sortTasksByPriority() {

        return tasks.stream()
                .sorted(
                        Comparator.comparingInt(
                                (Task task) -> task.getPriority().ordinal()
                        ).reversed()
                )
                .toList();
    }

    public List<Task> sortTasksByDueDate() {

        return tasks.stream()
                .sorted(
                        Comparator.comparing(Task::getDueDate)
                )
                .toList();
    }

    public Map<TaskStatus, Long> getTaskStatistics() {

        Map<TaskStatus, Long> statistics =
                new EnumMap<>(TaskStatus.class);

        for (TaskStatus status : TaskStatus.values()) {

            long count = tasks.stream()
                    .filter(task -> task.getStatus() == status)
                    .count();

            statistics.put(status, count);
        }

        return statistics;
    }


}


