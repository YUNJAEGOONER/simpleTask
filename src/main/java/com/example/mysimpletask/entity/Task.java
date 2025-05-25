package com.example.mysimpletask.entity;

import lombok.Getter;

import java.util.Date;

@Getter
public class Task {

    //PK
    public Long id;

    public String task;

    public String user;

    public String pw;

//    public Timestamp createdAt;
//    public Timestamp modifiedAt;
    public Date createdAt;
    public Date modifiedAt;

    //작성일과 수정일은 TIMESTAMP
    //작성일 createdAt
    //수정일 modifiedAt

    public Task(){};

    //createMethod 수행 시,
    public Task(String task, String user, String pw){
        this.task = task;
        this.user = user;
        this.pw = pw;
    }


    public Task(long id, String task, String user, Date createdAt, Date modifiedAt) {
        this.id = id;
        this.task = task;
        this.user = user;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
