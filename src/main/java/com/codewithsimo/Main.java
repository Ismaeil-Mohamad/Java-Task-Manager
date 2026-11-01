package com.codewithsimo;


import com.codewithsimo.service.TaskService;
import com.codewithsimo.ui.TaskMenu;

public class Main {
    public static void main(String[] args) {

        TaskService taskService = new TaskService();
        TaskMenu taskMenu = new TaskMenu(taskService);
        taskMenu.showMenu();
    }
}
