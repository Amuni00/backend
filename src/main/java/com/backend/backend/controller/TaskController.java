package com.backend.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.backend.dto.TaskDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.entity.Task;
import com.backend.backend.entity.User;
import com.backend.backend.service.TaskService;
import com.backend.backend.exception.ValidationException;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create Task
    @PostMapping("create-task")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task savedTask = taskService.createTask(task);
            return ResponseEntity.ok("task created successfully");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    //assign task
    @PutMapping("assign-task/{id}")
    public ResponseEntity<?> assigneeTask(
            @PathVariable Long id,
            @RequestBody Map<String, Long> requestBody) {

        try {
            Long assigneeId = requestBody.get("assignee");
            if (assigneeId == null) {
                return ResponseEntity.badRequest().body("Missing 'assignee' in request body");
            }

            Task taskAssigned = taskService.assignTask(id, assigneeId);
            return ResponseEntity.ok("Task assigned successfully");

        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Update Task
    @PutMapping("update-task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok("task updated succesfully");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

 // Get all tasks
    @GetMapping("/get-tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks(); // fetches entities

        List<TaskDto> dtoList = tasks.stream()
                .map(task -> new TaskDto(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getDueDate(),
                        task.getPriority(),
                        task.getCreator() != null ? task.getCreator().getId() : null, // handle null creator
                        task.getAssignee() != null ? task.getAssignee().getId() : null // handle null assignee
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }


    // Delete task
    @DeleteMapping("delete-task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task deleted successfully with id " + id);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
