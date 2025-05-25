package com.example.mysimpletask.dto;

import com.example.mysimpletask.entity.Task;

import java.util.Date;

public class TaskResponseDto {
    public String task;

    public String user;

//    public Timestamp createdAt;
//    public Timestamp modifiedAt;

    public Date createdAt;
    public Date modifiedAt;

    public TaskResponseDto(Task task){
        this.task = task.getTask();
        this.user = task.getUser();
        this.createdAt = task.getCreatedAt();
        this.modifiedAt = task.getModifiedAt();
    }

    public TaskResponseDto(String task, String user, Date createdAt, Date modifiedAt) {
        this.task = task;
        this.user = user;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public TaskResponseDto(String task, String user, String pw, Date createdAt, Date modifiedAt) {
        this.task = task;
        this.user = user;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


}
