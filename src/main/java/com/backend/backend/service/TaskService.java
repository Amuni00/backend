package com.backend.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.backend.entity.Role;
import com.backend.backend.entity.Task;
import com.backend.backend.entity.User;
import com.backend.backend.repositories.TaskRepository;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.exception.ValidationException;
import com.backend.backend.validation.ValidationTask;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;

	public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	public Task createTask(Task task) {
		ValidationTask.validate(task); // throws TaskValidationException if invalid
		User creatorId = userRepository.findById(task.getCreator().getId()).orElseThrow(
				() -> new ValidationException("User with ID " + task.getCreator().getId() + " does not exist."));
		
		task.setCreator(creatorId);

		return taskRepository.save(task);
	}
	public Task assignTask(Long taskId, Long assigneeId) {
	    // 1️⃣ Find the task by ID
	    Task existingTask = taskRepository.findById(taskId)
	            .orElseThrow(() -> new ValidationException("Task not found with ID " + taskId));

	    // 2️⃣ Find the user (assignee) by ID
	    User assignee = userRepository.findById(assigneeId)
	            .orElseThrow(() -> new ValidationException("User with ID " + assigneeId + " does not exist."));

	    // 3️⃣ Assign the user to the task
	    existingTask.setAssignee(assignee);

	    // 4️⃣ Save and return the updated task
	    return taskRepository.save(existingTask);
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
