package com.backend.backend.validation;

import java.time.LocalDate;

import com.backend.backend.entity.Task;
import com.backend.backend.exception.ValidationException;

public class ValidationTask {

    public static void validate(Task task) {
        // Title validation
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new ValidationException("Task title cannot be empty");
        }

        // Priority validation
        if (!task.getPriority().equalsIgnoreCase("HIGH") &&
            !task.getPriority().equalsIgnoreCase("MEDIUM") &&
            !task.getPriority().equalsIgnoreCase("LOW")) {
            throw new ValidationException("Priority must be HIGH, MEDIUM, or LOW");
        }

        // Status validation
        if (!task.getStatus().equalsIgnoreCase("PENDING") &&
            !task.getStatus().equalsIgnoreCase("IN_PROGRESS") &&
            !task.getStatus().equalsIgnoreCase("COMPLETED")) {
            throw new ValidationException("Status must be PENDING, IN_PROGRESS, or COMPLETED");
        }

        // Due date validation
        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Due date cannot be in the past");
        }
    }
}
