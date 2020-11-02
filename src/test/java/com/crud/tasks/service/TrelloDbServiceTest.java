package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloDbServiceTest {
    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    private List<Task> tasks;
    private Task task1;
    private Task task2;
    private Task task3;

    @Before
    public void init() {
        tasks = new ArrayList<>();
        task1 = new Task(1L, "task1", "content1");
        task2 = new Task(2L, "task2", "content2");
        task3 = new Task(3L, "task3", "content3");

        tasks.add(task1);
        tasks.add(task2);
    }

    @Test
    public void shouldFetchAllTask() {
        //given
        when(taskRepository.findAll()).thenReturn(tasks);
        //when
        List<Task> fetchedTasks = dbService.getAllTasks();
        //then
        assertEquals(tasks.size(), fetchedTasks.size());
        assertEquals(tasks.get(0).getId(), fetchedTasks.get(0).getId());
        assertEquals(tasks.get(0).getTitle(), fetchedTasks.get(0).getTitle());
        assertEquals(tasks.get(1).getId(), fetchedTasks.get(1).getId());
    }

    @Test
    public void shouldFetchTask() {
        //given
        when(taskRepository.findById(task1.getId())).thenReturn(Optional.ofNullable(task1));
        //when
        Optional<Task> fetchedTask = dbService.getTask(task1.getId());
        //then
        assertNotNull(fetchedTask);
        assertEquals(task1.getId(),fetchedTask.get().getId());
        assertEquals(task1.getTitle(),fetchedTask.get().getTitle());
        assertEquals(task1.getContent(),fetchedTask.get().getContent());
    }

    @Test
    public void saveTaskTest() {
        //given
        when(taskRepository.save(task3)).thenReturn(task3);
        //when
        Task savedTask = dbService.saveTask(task3);
        //Then
        assertEquals(task3.getTitle(),savedTask.getTitle());
        assertEquals(task3.getContent(),savedTask.getContent());
        assertEquals(task3.getId(),Long.valueOf(savedTask.getId()));
        assertEquals(3L,savedTask.getId().longValue());
    }

    @Test
    public void deleteTaskByIdTest() {
        //given
        when(taskRepository.save(task1)).thenReturn(task1);

        //when
        Task savedTask = dbService.saveTask(task1);

        dbService.deleteTaskById(savedTask.getId().longValue());
        //then
        assertEquals(taskRepository.findAll().size(),dbService.getAllTasks().size());
        assertEquals(0,dbService.getAllTasks().size());
    }
}