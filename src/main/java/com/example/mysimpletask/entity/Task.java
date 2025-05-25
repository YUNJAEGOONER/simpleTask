package com.example.mysimpletask.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class Task {

    //PK
    private Long id;

    private String task;

    private String user;

    private String pw;


    private final LocalDateTime createdAt = LocalDateTime.now().withNano(0);
    private LocalDateTime modifiedAt = this.createdAt;

    //createMethod 수행 시,
    public Task(String task, String user, String pw){
        this.task = task;
        this.user = user;
        this.pw = pw;
    }

    public Task(long id, String task, String user, LocalDateTime modifiedAt) {
        this.id = id;
        this.task = task;
        this.user = user;
        this.modifiedAt = modifiedAt;
    }

}
