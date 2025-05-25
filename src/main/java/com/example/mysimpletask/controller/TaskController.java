package com.example.mysimpletask.controller;

import com.example.mysimpletask.Service.TaskService;
import com.example.mysimpletask.dto.TaskRequestDto;
import com.example.mysimpletask.dto.TaskResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@ModelAttribute TaskRequestDto dto){
        return new ResponseEntity<>(taskService.saveTask(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTasks(
            @RequestParam(value = "modifiedAt", required = false) String date, @RequestParam(value = "user", required = false) String user){
        return new ResponseEntity<>(taskService.findTaksByDateAndUser(date, user), HttpStatus.OK);
    }


    //고유 식별자를 사용한 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findTaskByID(@PathVariable Long id){
        return new ResponseEntity<>(taskService.findTaskByID(id), HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteMemo(@PathVariable Long id, @Re)

}
