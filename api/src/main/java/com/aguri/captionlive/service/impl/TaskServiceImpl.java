package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.common.exception.OperationNotAllowException;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.repository.TaskRepository;
import com.aguri.captionlive.repository.UserRepository;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.TaskService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        return taskOptional
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        // Update the task properties
        existingTask.setType(task.getType());
        existingTask.setStatus(task.getStatus());
        existingTask.setAcceptedTime(task.getAcceptedTime());
        existingTask.setFile(task.getFile());
        // Update other properties as needed

        // Save the updated task
        return taskRepository.save(existingTask);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public Task commitTask(Long taskId) {
        Task existingTask = getTaskById(taskId);
        existingTask.setStatus(Task.Status.COMPLETED);
        return taskRepository.save(existingTask);
    }

    @Override
    public Task withdrawalTask(Long taskId) {
        Task existingTask = getTaskById(taskId);
        existingTask.setStatus(Task.Status.IN_PROGRESS);
        return taskRepository.save(existingTask);
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public Task assign(Long taskId, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        User user = new User();
        user.setUserId(userId);
        Task existingTask = getTaskById(taskId);
        if (existingTask.getStatus() != Task.Status.NOT_ASSIGNED) {
            throw new OperationNotAllowException("task status is not " + Task.Status.NOT_ASSIGNED);
        }
        existingTask.setWorker(user);
        existingTask.setStatus(Task.Status.IN_PROGRESS);
        return taskRepository.save(existingTask);
    }


    @Autowired
    private FileRecordService fileRecordService;

    @Override
    public Task saveFile(Long taskId, MultipartFile file) {
        Task task = getTaskById(taskId);
        if (!file.isEmpty()) {
                FileRecord fileRecord = fileRecordService.saveSmallSizeFile(file, "file" + File.separator + "task" + File.separator + taskId.toString());
                task.setFile(fileRecord);
        }
        return updateTask(taskId, task);
    }
}