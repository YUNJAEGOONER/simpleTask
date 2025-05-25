package com.example.mysimpletask.dto;

import com.example.mysimpletask.entity.Task;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class TaskResponseDto {

    private Long id;

    private String task;

    private String user;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TaskResponseDto(Task task){
        this.id = task.getId();
        this.task = task.getTask();
        this.user = task.getUser();
        this.createdAt = task.getCreatedAt();
        this.modifiedAt = task.getModifiedAt();
    }

    public TaskResponseDto(Long id, String task, String user, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.task = task;
        this.user = user;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
