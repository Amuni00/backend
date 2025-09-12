package com.backend.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.backend.entity.Task;
import com.backend.backend.entity.UserEntity;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByAssignee(UserEntity assignee);
}
