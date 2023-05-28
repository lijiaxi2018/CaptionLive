package com.aguri.captionlive.controller;

import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    public FileRecordService fileRecordService;

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(Resp.ok(task));
    }

    @PostMapping
    public ResponseEntity<Resp> createTask(@RequestBody Task task) {
        Task createdTask = taskService.saveTask(task);
        return ResponseEntity.ok(Resp.ok(createdTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resp> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(Resp.ok(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resp> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/commit/{taskId}/{userId}")
    public ResponseEntity<Resp> commit(@PathVariable Long taskId, @PathVariable Long userId) {
        Task updatedTask = taskService.commitTask(taskId);
        return ResponseEntity.ok(Resp.ok(updatedTask));
    }

    @PutMapping("/withdrawal/{taskId}/{userId}")
    public ResponseEntity<Resp> withdrawal(@PathVariable Long taskId, @PathVariable Long userId) {
        Task updatedTask = taskService.withdrawalTask(taskId);
        return ResponseEntity.ok(Resp.ok(updatedTask));
    }

    @PutMapping("/assign/{taskId}/{userId}")
    public ResponseEntity<Resp> assign(@PathVariable Long taskId, @PathVariable Long userId) {
        Task updatedTask = taskService.assign(taskId, userId);
        return ResponseEntity.ok(Resp.ok(updatedTask));
    }

    @PostMapping("/uploadFile/{taskId}")
    public ResponseEntity<Resp> uploadFile(@PathVariable Long taskId, @RequestParam MultipartFile file) {
        Task task = taskService.saveFile(taskId,file);
        return ResponseEntity.ok(Resp.ok(task));
    }
    @GetMapping("/downloadFile/{taskId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        FileRecord fileRecord = task.getFile();
        return fileRecordService.download(fileRecord);
    }


}
