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

    //LV1.일정 생성 (Create)
    //HTML Form을 통한 입력 받기
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@ModelAttribute TaskRequestDto dto){
        return new ResponseEntity<>(taskService.saveTask(dto), HttpStatus.CREATED);
    }

    //LV1. 전체 일정 조회(Read)
    //특정 조건(수정일, 작성자명)을 만족하는 모든 일정 가져오기
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTasks(
            @RequestParam(value = "date", required = false) String date, @RequestParam(value = "user", required = false) String user){
        return new ResponseEntity<>(taskService.findTaksByDateAndUser(date, user), HttpStatus.OK);
    }
    
    //LV1. 선택한 일정 조회(Read)
    //고유 식별자를 사용한 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findTaskByID(@PathVariable Long id){
        return new ResponseEntity<>(taskService.findTaskByID(id), HttpStatus.OK);
    }
    
    //LV2. 선택한 일정 수정(Update)
    //일부 내역만을 수정 -> patch
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDto> modifyUserAndTask(
            @PathVariable Long id, @ModelAttribute TaskRequestDto dto){
        return new ResponseEntity<>(taskService.modifyUserAndTask(id, dto), HttpStatus.OK);
    }
    
    //LV2. 선택한 일정 삭제(Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo
            (@PathVariable Long id, @RequestParam(value = "pw") String pw){
        taskService.deleteTask(id, pw);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
