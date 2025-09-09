package com.backend.backend.service;

import com.backend.backend.entity.Task;
import com.backend.backend.entity.User;
import com.backend.backend.exception.ValidationException;
import com.backend.backend.repositories.TaskRepository;
import com.backend.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    
    @Override
    public Task assignTask(Long taskId, Long assigneeId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ValidationException("Task not found with id " + taskId));

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new ValidationException("User not found with id " + assigneeId));

        task.setAssignee(assignee);

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Task not found with id " + id));
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        existing.setDueDate(task.getDueDate());
        existing.setPriority(task.getPriority());
        existing.setCreator(task.getCreator());
        existing.setAssignee(task.getAssignee());
        return taskRepository.save(existing);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) throw new ValidationException("Task not found with id " + id);
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

}
