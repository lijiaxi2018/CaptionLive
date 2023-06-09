package com.aguri.captionlive.service.impl;

import com.aguri.captionlive.common.exception.EntityNotFoundException;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.repository.TaskRepository;
import com.aguri.captionlive.repository.UserRepository;
import com.aguri.captionlive.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

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
    public Task commitTask(Long taskId, Long userId) {
        Task existingTask = taskRepository.getReferenceById(taskId);
        if (!Objects.equals(existingTask.getWorker().getUserId(), userId)) {
            throw new RuntimeException("The current user has not accepted the task");
        }

        if (existingTask.getStatus() != Task.Status.IN_PROGRESS) {
            throw new RuntimeException("Cannot commit task in its current state");
        }

        existingTask.setStatus(Task.Status.COMPLETED);
        return taskRepository.save(existingTask);
    }

    @Override
    public Task withdrawalCommit(Long taskId, Long userId) {
        Task existingTask = taskRepository.getReferenceById(taskId);
        if (!Objects.equals(existingTask.getWorker().getUserId(), userId)) {
            throw new RuntimeException("The current user has not accepted the task");
        }

        if (existingTask.getStatus() != Task.Status.COMPLETED) {
            throw new RuntimeException("Cannot withdraw task in its current state");
        }

        existingTask.setStatus(Task.Status.IN_PROGRESS);
        return taskRepository.save(existingTask);
    }

    @Override
    public Task withdrawalAssign(Long taskId, Long userId) {
        Task existingTask = taskRepository.getReferenceById(taskId);
        if (!Objects.equals(existingTask.getWorker().getUserId(), userId)) {
            throw new RuntimeException("The current user has not accepted the task");
        }

        if (existingTask.getStatus() != Task.Status.IN_PROGRESS) {
            throw new RuntimeException("Cannot withdraw task in its current state");
        }

        existingTask.setStatus(Task.Status.NOT_ASSIGNED);
        return taskRepository.save(existingTask);
    }

    @Override
    public Task assign(Long taskId, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Task existingTask = taskRepository.getReferenceById(taskId);

        if (existingTask.getStatus() != Task.Status.NOT_ASSIGNED) {
            throw new RuntimeException("Cannot assign task in its current state");
        }

        existingTask.setWorker(user);
        existingTask.setStatus(Task.Status.IN_PROGRESS);
        return taskRepository.save(existingTask);
    }

}