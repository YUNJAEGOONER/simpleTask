package com.example.mysimpletask.Service;

import com.example.mysimpletask.dto.TaskRequestDto;
import com.example.mysimpletask.dto.TaskResponseDto;
import com.example.mysimpletask.entity.Task;
import com.example.mysimpletask.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponseDto saveTask(TaskRequestDto dto) {
        Task task = new Task(dto.getTask(), dto.getUser(), dto.getPw());
        TaskResponseDto saved = taskRepository.save(task);
        return saved;
    }

    @Override
    public TaskResponseDto findTaskByID(Long id) {
        Task found = taskRepository.findTaskByID(id);
        return new TaskResponseDto(found);
    }

    @Override
    public List<TaskResponseDto> findTaksByDateAndUser(String date, String user) {
        return taskRepository.findTaskByDateAndUser(date, user);
    }
}
