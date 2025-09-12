package com.backend.backend.service;

import com.backend.backend.entity.Task;
import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task assignTask(Long taskId, Long assigneeId);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    List<Task> getAllTasks();
    List<Task> getAssigneesTaskById(Long id);
}
