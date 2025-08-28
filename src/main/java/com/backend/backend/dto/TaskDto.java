package com.backend.backend.dto;

import java.time.LocalDate;

public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
    private String priority;

    private String creatorName;   // instead of embedding full User
    private String assigneeName;

    public TaskDto() {}

    public TaskDto(Long id, String title, String description, String status, LocalDate dueDate, 
                   String priority, String creatorName, String assigneeName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.priority = priority;
        this.creatorName = creatorName;
        this.assigneeName = assigneeName;
    }

    // Getters and Setters
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

    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreatorName() {
        return creatorName;
    }
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }
    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
}
