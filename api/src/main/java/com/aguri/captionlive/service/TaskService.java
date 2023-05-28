package com.aguri.captionlive.service;

import com.aguri.captionlive.model.Task;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {
    Task saveTask(Task task);

    Task getTaskById(Long taskId);

    List<Task> getAllTasks();

    Task updateTask(Long id, Task task);

    void deleteTask(Long taskId);

    Task commitTask(Long taskId);

    Task withdrawalTask(Long taskId);

    Task assign(Long taskId, Long userId);

    Task saveFile(Long taskId, MultipartFile file);
}