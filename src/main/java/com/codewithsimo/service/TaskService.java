package  com.codewithsimo.service;

import com.codewithsimo.enums.Priority;
import com.codewithsimo.enums.TaskStatus;
import com.codewithsimo.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private List<Task> tasks = new ArrayList<>();
    private int nextTaskId;

    public TaskService() {
        this.tasks = new ArrayList<>();
        this.nextTaskId = 1;
    }

    public Task addTask(String title, String description, Priority priority, LocalDate dueDate) {
        Task task = new Task(nextTaskId,title,description, priority,dueDate);
        tasks.add(task);
        nextTaskId++;
        return task;
    }

public Optional<Task>getAllTasks(int id){
        
        return tasks.stream()
                .filter(task-> task.getId()== id)
                .findFirst();
        }



}



