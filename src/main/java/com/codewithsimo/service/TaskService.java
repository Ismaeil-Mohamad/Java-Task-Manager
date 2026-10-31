package  com.codewithsimo.service;

import com.codewithsimo.enums.Priority;
import com.codewithsimo.enums.TaskStatus;
import com.codewithsimo.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private List<Task> tasks = new ArrayList<>();
    private int nextTaskId = 1;

    public TaskService() {
    }

    public Task addTask(String title, String description, Priority priority, LocalDate dueDate) {
        Task task = new Task(nextTaskId,title,description, priority,dueDate);
        tasks.add(task);
        nextTaskId++;
        return task;
    }



}



