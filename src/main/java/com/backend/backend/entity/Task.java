package com.backend.backend.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "Task_Table")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 200)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private String status = "PENDING";

	private LocalDate dueDate;

	@ManyToOne
	@JoinColumn(name = "creator_id", referencedColumnName = "id")
	private UserEntity creator;

	 @ManyToOne
	 @JoinColumn(name = "assignee_id", referencedColumnName = "id")
	private UserEntity assignee;

	@Column(nullable = false)
	private String priority; // e.g., LOW, MEDIUM, HIGH
	
	private String remark;

	// Constructors
	public Task() {
	}

	public Task(String title, String description, String status, LocalDate dueDate, UserEntity creator, UserEntity assignee, String priority,String remark) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.dueDate = dueDate;
		this.creator = creator;
		this.assignee = assignee;
		this.priority = priority;
		this.remark=remark;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public UserEntity getCreator() { return creator; }
	public void setCreator(UserEntity creator) { this.creator = creator; }

	public UserEntity getAssignee() { return assignee; }
	public void setAssignee(UserEntity assignee) { this.assignee = assignee; }

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
