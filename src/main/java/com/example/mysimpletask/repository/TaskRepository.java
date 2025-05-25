package com.example.mysimpletask.repository;

import com.example.mysimpletask.dto.TaskRequestDto;
import com.example.mysimpletask.dto.TaskResponseDto;
import com.example.mysimpletask.entity.Task;

import java.util.List;

public interface TaskRepository {
    TaskResponseDto save(Task task);
    Task findTaskByID(Long id);
    List<TaskResponseDto> findTaskByDateAndUser(String date, String user);
    int  updateTaskAndUser(String pw, String task, String user, Long id);
}
