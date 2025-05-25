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
        return null;
    }

    @Override
    public Task findTaskByID(Long id) {
        List<Task> result = jdbcTemplate.query("SELECT * FROM task WHERE id = ?", taskRowMapper(), id);
        System.out.println("size =" + result.size());
        return result.stream().findAny()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    @Override
    public List<TaskResponseDto> findTaskByDateAndUser(String date, String user) {
        if(date == null && user != null){
            return jdbcTemplate.query(
                    "SELECT * " +
                    "FROM task " +
                    "WHERE user = ?" +
                    "ORDER BY modifiedAt DESC ", taskRowMapperv2(), user);
        }
        else if(date != null && user == null){
            return jdbcTemplate.query(
                    "SELECT * " +
                    "FROM task " +
                    "WHERE DATE_FORMAT(modifiedAt, '%Y-%m-%d') = ?" +
                    "ORDER BY modifiedAt DESC ", taskRowMapperv2(), date);
        }
        else if(date == null && user == null){
            return jdbcTemplate.query(
                    "SELECT * " +
                    "FROM task " +
                    "ORDER BY modifiedAt DESC ", taskRowMapperv2());
        }
        return jdbcTemplate.query(
                "SELECT * " +
                "FROM task " +
                "WHERE user = ? AND DATE_FORMAT(modifiedAt, '%Y-%m-%d') = ?" +
                "ORDER BY modifiedAt DESC ", taskRowMapperv2(), user, date);
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

    private RowMapper<TaskResponseDto> taskRowMapperv2(){
        return new RowMapper<TaskResponseDto>() {
            @Override
            public TaskResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskResponseDto(
                        rs.getString("task"),
                        rs.getString("user"),
                        rs.getDate("createdAt"),
                        rs.getDate("modifiedAt")
                );
            }
        };
    }



}
