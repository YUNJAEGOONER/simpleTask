package com.example.mysimpletask.Service;

import com.example.mysimpletask.dto.TaskRequestDto;
import com.example.mysimpletask.dto.TaskResponseDto;
import com.example.mysimpletask.entity.Task;
import com.example.mysimpletask.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public List<TaskResponseDto> findTaksByDateAndUser(String date, String user) {
        return taskRepository.findTaskByDateAndUser(date, user);
    }

    @Override
    public TaskResponseDto findTaskByID(Long id) {
        Task found = taskRepository.findTaskByID(id);
        return new TaskResponseDto(found);
    }

    @Override
    public TaskResponseDto modifyUserAndTask(Long id, TaskRequestDto dto) {
        Task found = taskRepository.findTaskByID(id); //found에 실패하면 not found throw;
        
        int updated = taskRepository.updateTaskAndUser(dto.getPw(), dto.getTask(), dto.getUser(), id);
        //영향을 받는 row의 수가 0인 경우 -> 비밀번호가 일치하지 않는 경우
        if(updated == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Password!!!");
        }

        Task task = taskRepository.findTaskByID(id);
        return new TaskResponseDto(task);
    }

    @Override
    public void deletTask(Long id, String pw) {
        Task found = taskRepository.findTaskByID(id); // found에 실패하면 not found throw;
        int deleted = taskRepository.deleteTask(id, pw);
        if(deleted == 0){
            //비밀번호가 일치하지 않는 경우
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Password!!!");
        }
    }

}
