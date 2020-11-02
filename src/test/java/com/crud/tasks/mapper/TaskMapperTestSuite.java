package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {

    @InjectMocks
    private TaskMapper mapper;

    private TaskDto taskDto1;
    private TaskDto taskDto2;
    private TaskDto taskDto3;
    private Task task1;
    private Task task2;
    private Task task3;
    List<TaskDto> taskDtosList;
    List<Task> taskList;

    @Before
    public void init() {

        String title1 = "doTask1";
        String title2 = "doTask2";
        String title3 = "doTask3";

        String content1 = "We're dooing task1";
        String content2 = "We're dooing task2";
        String content3 = "We're dooing task3";

        taskDto1 = new TaskDto(1L, title1, content1);
        taskDto2 = new TaskDto(2L, title2, content2);
        taskDto3 = new TaskDto(3L, title3, content3);

        task1 = new Task(1L, title1, content1);
        task2 = new Task(2L, title2, content2);
        task3 = new Task(3L, title3, content3);

        taskDtosList = new ArrayList();
        taskList = new ArrayList<>();

        taskDtosList.add(taskDto1);
        taskDtosList.add(taskDto2);
        taskDtosList.add(taskDto3);

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
    }

    @Test
    public void testMapToTask() {
        //Given in init

        //When
        Task task1 = mapper.mapToTask(taskDto1);
        Task task2 = mapper.mapToTask(taskDto2);
        Task task3 = mapper.mapToTask(taskDto3);
        //Then
        assertEquals(taskDto1.getId(), task1.getId());
        assertEquals(taskDto2.getContent(), task2.getContent());
        assertEquals(taskDto3.getTitle(), task3.getTitle());
    }

    @Test
    public void testMapToTaskDto() {
        //Given in init

        //When
        TaskDto taskDto1 = mapper.mapToTaskDto(task1);
        TaskDto taskDto2 = mapper.mapToTaskDto(task2);
        TaskDto taskDto3 = mapper.mapToTaskDto(task3);
        //Then
        assertEquals(task2.getId(), taskDto2.getId());
        assertEquals(task3.getContent(), taskDto3.getContent());
        assertEquals(task1.getTitle(), taskDto1.getTitle());
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given in init

        //When
        List<TaskDto> mappedTaskDtoList = mapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(taskList.size(),mappedTaskDtoList.size());
        assertEquals(taskList.get(0).getId(),mappedTaskDtoList.get(0).getId());
        assertEquals(taskList.get(1).getContent(),mappedTaskDtoList.get(1).getContent());
        assertEquals(taskList.get(2).getTitle(),mappedTaskDtoList.get(2).getTitle());
        assertEquals("doTask3", mappedTaskDtoList.get(2).getTitle());


    }

}