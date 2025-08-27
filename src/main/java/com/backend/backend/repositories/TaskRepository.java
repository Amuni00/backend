package com.backend.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.backend.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
