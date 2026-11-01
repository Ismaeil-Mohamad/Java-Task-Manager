package com.codewithsimo.util;

import java.time.LocalDate;

public class TaskValidator {
    public static  boolean isValidTitle(String title){
        return title != null && !title.trim().isEmpty();
    }
    public static boolean isValidDescription(String description){
        return  description != null && !description.trim().isEmpty();
    }
    public static boolean isValidDueDate(LocalDate dueDate){
        return dueDate != null;
    }
}
