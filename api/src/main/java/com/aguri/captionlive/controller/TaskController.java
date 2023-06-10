package com.aguri.captionlive.controller;

import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    public FileRecordService fileRecordService;

//    @GetMapping("/{id}")
//    public ResponseEntity<Resp> getTaskById(@PathVariable Long id) {
//        Task task = taskService.getTaskById(id);
//        return ResponseEntity.ok(Resp.ok(task));
//    }

//    @PostMapping
//    public ResponseEntity<Resp> createTask(@RequestBody Task task) {
//        Task createdTask = taskService.saveTask(task);
//        return ResponseEntity.ok(Resp.ok(createdTask));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Resp> updateTask(@PathVariable Long id, @RequestBody Task task) {
//        Task updatedTask = taskService.updateTask(id, task);
//        return ResponseEntity.ok(Resp.ok(updatedTask));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Resp> deleteTask(@PathVariable Long id) {
//        taskService.deleteTask(id);
//        return ResponseEntity.ok(Resp.ok());
//    }

//    @PutMapping("/{taskId}/commit/{userId}")
//    public ResponseEntity<Resp> commit(@PathVariable Long taskId, @PathVariable Long userId) {
//        Task updatedTask = taskService.commitTask(taskId, userId);
//        return ResponseEntity.ok(Resp.ok(updatedTask));
//    }

//    @DeleteMapping("/{taskId}/commit/{userId}")
//    public ResponseEntity<Resp> withdrawalCommit(@PathVariable Long taskId, @PathVariable Long userId) {
//        Task updatedTask = taskService.withdrawalCommit(taskId, userId);
//        return ResponseEntity.ok(Resp.ok(updatedTask));
//    }

    @PutMapping("/{taskId}/assign/{userId}")
    @Operation(summary = "Assign task to user", description = "Assign a task to a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task assigned successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    public ResponseEntity<Resp> assign(
            @PathVariable("taskId") Long taskId,
            @PathVariable("userId") Long userId) {
        Task updatedTask = taskService.assign(taskId, userId);
        return ResponseEntity.ok(Resp.ok(updatedTask));
    }

    @DeleteMapping("/{taskId}/assign")
    @Operation(summary = "Withdraw assignment", description = "Withdraw the assignment of a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment withdrawal successful",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    public ResponseEntity<Resp> withdrawalAssign(
            @PathVariable("taskId") Long taskId) {
        Task updatedTask = taskService.withdrawalAssign(taskId);
        return ResponseEntity.ok(Resp.ok(updatedTask));
    }

    @PutMapping("/{taskId}/file/{fileRecordId}")
    @Operation(summary = "Upload file and update task status", description = "Upload a file and update the task status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded and task status changed successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    public ResponseEntity<Resp> uploadFileAndTaskStatusChange(
            @PathVariable("taskId") Long taskId,
            @PathVariable("fileRecordId") Long fileRecordId) {
        return ResponseEntity.ok(Resp.ok(taskService.uploadFileAndTaskStatusChange(taskId, fileRecordId)));
    }


}
