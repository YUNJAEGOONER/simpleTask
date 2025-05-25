package com.example.mysimpletask.Service;

import com.example.mysimpletask.dto.TaskRequestDto;
import com.example.mysimpletask.dto.TaskResponseDto;

import java.util.List;


public interface TaskService {
    TaskResponseDto saveTask(TaskRequestDto dto);
    TaskResponseDto findTaskByID(Long id);
    List<TaskResponseDto> findTaksByDateAndUser(String date, String user);
    TaskResponseDto modifyUserAndTask(Long id, TaskRequestDto dto);
    void deleteTask(Long id, String pw);
}
