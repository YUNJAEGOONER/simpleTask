package com.example.mysimpletask.repository;

import com.example.mysimpletask.dto.TaskResponseDto;
import com.example.mysimpletask.entity.Task;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateTaskRepository implements TaskRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TaskResponseDto save(Task task) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        //table이름 명시
        jdbcInsert.withTableName("task").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", task.getTask());
        parameters.put("user", task.getUser());
        parameters.put("pw", task.getPw());

        //현재시간
        Date now = new Date();
        parameters.put("createdAt", now);
        parameters.put("modifiedAt", now);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new TaskResponseDto(key.longValue(), task.getTask(), task.getUser(), task.getCreatedAt(), task.getModifiedAt());
    }

    @Override
    public List<TaskResponseDto> findTaskByDateAndUser(String date, String user) {
        //[공통] 수정일을 기준으로 내림차순
        //사용자를 통한 검색
        if(date == null && user != null){
            return jdbcTemplate.query(
                    "SELECT * " +
                            "FROM task " +
                            "WHERE user = ?" +
                            "ORDER BY modifiedAt DESC ", taskRowMapperv2(), user);
        }
        //수정일을 통한 검색
        else if(date != null && user == null){
            return jdbcTemplate.query(
                    "SELECT * " +
                            "FROM task " +
                            "WHERE DATE_FORMAT(modifiedAt, '%Y-%m-%d') = ?" +
                            "ORDER BY modifiedAt DESC ", taskRowMapperv2(), date);
        }
        //수정일, 사용자 모두 전달되지 않은 경우
        else if(date == null && user == null){
            return jdbcTemplate.query(
                    "SELECT * " +
                            "FROM task " +
                            "ORDER BY modifiedAt DESC ", taskRowMapperv2());
        }
        //수정일, 사용자 모두 전달된 경우
        return jdbcTemplate.query(
                "SELECT * " +
                        "FROM task " +
                        "WHERE user = ? AND DATE_FORMAT(modifiedAt, '%Y-%m-%d') = ?" +
                        "ORDER BY modifiedAt DESC ", taskRowMapperv2(), user, date);
    }

//    @Override
//    public TaskResponseDto findTaskByID(Long id) {
//        List<TaskResponseDto> result = jdbcTemplate.query("SELECT * FROM task WHERE id = ?", taskRowMapperv2(), id);
//        return result.stream().findAny()
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
//    }

    @Override
    public Task findTaskByID(Long id) {
        List<Task> result = jdbcTemplate.query("SELECT * FROM task WHERE id = ?", taskRowMapper(), id);
        return result.stream().findAny()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    @Override
    public int updateTaskAndUser(String pw, String task, String user, Long id) {
        Date now = new Date();
        int updatedRow = jdbcTemplate.update("UPDATE task SET task = ?, user = ?, modifiedAt =? " +
                "WHERE id = ? AND pw = ?", task, user, now, id, pw);
        return updatedRow;
    }

    @Override
    public int deleteTask(Long id, String pw) {
        return jdbcTemplate.update("DELETE FROM task where id = ? AND pw = ?", id, pw);
    }

    private RowMapper<TaskResponseDto> taskRowMapperv2(){
        return new RowMapper<TaskResponseDto>() {
            @Override
            public TaskResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskResponseDto(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("user"),
                        rs.getDate("createdAt"),
                        rs.getDate("modifiedAt")
                );
            }
        };
    }

    private RowMapper<Task> taskRowMapper(){
        return new RowMapper<Task>() {
            @Override
            public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Task(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("user"),
                        rs.getDate("createdAt"),
                        rs.getDate("modifiedAt")
                );
            }
        };
    }

}
