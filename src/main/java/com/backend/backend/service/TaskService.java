package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.Task;
import com.backend.backend.repositories.TaskRepository;
import com.backend.backend.exception.ValidationException;
import com.backend.backend.validation.ValidationTask;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        ValidationTask.validate(task); // throws TaskValidationException if invalid
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Task not found with id " + id));

        ValidationTask.validate(task); // validate

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        existing.setDueDate(task.getDueDate());
        existing.setPriority(task.getPriority());
        existing.setCreator(task.getCreator());
        existing.setAssignee(task.getAssignee());

        return taskRepository.save(existing);
    }
    
    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Delete task
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ValidationException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }
}
