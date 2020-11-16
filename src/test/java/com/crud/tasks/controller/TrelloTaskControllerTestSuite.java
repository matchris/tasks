package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TrelloTaskControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;
    private TaskDto taskDto;
    private Task task;
    private List<TaskDto> taskDtos;
    private List<Task> tasks;
    private String jsonContent;


    @Before
    public void init() {
        taskDtos = new ArrayList<>();
        LongStream.range(1, 6)
                .forEach(number -> taskDtos.add(new TaskDto(number, "title:" + number, "content:" + number)));
        tasks = new ArrayList<>();
        LongStream.range(1, 6)
                .forEach(number -> tasks.add(new Task(number, "title-" + number, "content-" + number)));
        taskDto = new TaskDto(1L, "Task", "Description");
        task = new Task(1L, "Task", "Description");
        Gson gson = new Gson();
        jsonContent = gson.toJson(taskDto);
    }

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);
        //When & Then
        mockMvc.perform(get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title:1")))
                .andExpect(jsonPath("$[0].content", is("content:1")));
        mockMvc.perform(get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[3].title", is("title:4")))
                .andExpect(jsonPath("$[4].content", is("content:5")));

        verify(dbService, times(2)).getAllTasks();
        verify(taskMapper, times(2)).mapToTaskDtoList(anyList());
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given we've got 5 tasks
        task = tasks.get(4);
        taskDto = taskDtos.get(4);
        when(dbService.getTask(anyLong())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(get("/v1/tasks/5")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskDtos.size())))
                .andExpect(jsonPath("$.title", is(taskDto.getTitle())))
                .andExpect(jsonPath("$.content", is("content:5")));
        verify(dbService, times(1)).getTask(anyLong());
        verify(taskMapper, times(1)).mapToTaskDto(any(Task.class));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        doNothing().when(dbService).deleteTaskById(7L);
        //When & Then
        mockMvc.perform(delete("/v1/tasks/7")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "7"))
                .andExpect(status().is(200));
        verify(dbService, times(1)).deleteTaskById(anyLong());
    }

    @Test
    public void createTaskTest() throws Exception {
        //Given
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(dbService.saveTask(any(Task.class))).thenReturn(task);

        //When&Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(dbService, times(1)).saveTask(any(Task.class));
        verify(taskMapper, times(1)).mapToTask(any(TaskDto.class));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        //when&then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(matchAll(
                        status().is(200),
                        (jsonPath("$.id", is(1))),
                        (jsonPath("$.title", is("Task"))),
                        (jsonPath("$.content", is("Description")))));
    }
}