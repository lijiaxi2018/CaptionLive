package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Task;

import java.util.List;

public interface TaskService {
    Task saveTask(Task task);

    Task getTaskById(Long taskId);

    List<Task> getAllTasks();

    Task updateTask(Long id, Task task);

    void deleteTask(Long taskId);

    Task commitTask(Long taskId, Long userId);

    Task withdrawalCommit(Long taskId, Long userId);

    Task withdrawalAssign(Long taskId, Long userId);


    Task assign(Long taskId, Long userId);
}