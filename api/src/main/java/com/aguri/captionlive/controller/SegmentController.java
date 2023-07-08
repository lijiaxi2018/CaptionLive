package com.aguri.captionlive.controller;

import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.Segment;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.repository.ProjectRepository;
import com.aguri.captionlive.service.RemarkService;
import com.aguri.captionlive.service.SegmentService;
import com.aguri.captionlive.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/segments")
public class SegmentController {

    @Autowired
    private SegmentService segmentService;

//    @GetMapping
//    public ResponseEntity<Resp> getAllSegments() {
//        List<Segment> segments = segmentService.getAllSegments();
//        return ResponseEntity.ok(Resp.ok(segments));
//    }
//
//    @PostMapping
//    public ResponseEntity<Resp> createSegment(@RequestBody Segment segment) {
//        Segment createdSegment = segmentService.saveSegment(segment);
//        return ResponseEntity.ok(Resp.ok(createdSegment));
//    }
//
//    @GetMapping("/{segmentId}")
//    public ResponseEntity<Resp> getSegmentById(@PathVariable Long segmentId) {
//        Segment segment = segmentService.getSegmentById(segmentId);
//        return ResponseEntity.ok(Resp.ok(segment));
//    }
//
    @DeleteMapping("/{segmentId}")
    public ResponseEntity<Resp> deleteSegment(@PathVariable Long segmentId) {
        segmentService.deleteSegment(segmentId);
        return ResponseEntity.noContent().build();
    }
//
//    @PutMapping("/{segmentId}")
//    public ResponseEntity<Resp> updateSegment(@PathVariable Long segmentId, @RequestBody Segment segment) {
//        Segment updatedSegment = segmentService.updateSegment(segmentId, segment);
//        return ResponseEntity.ok(Resp.ok(updatedSegment));
//    }
//
//    @GetMapping("/getAllTasks/{segmentId}")
//    public ResponseEntity<Resp> getAllTasks(@PathVariable Long segmentId) {
//        List<Task> tasks = segmentService.getSegmentById(segmentId).getTasks();
//        return ResponseEntity.ok(Resp.ok(tasks));
//    }

//    @Autowired
//    private RemarkService remarkService;
//
//    @PostMapping("/{segmentId}/remark")
//    @Operation(summary = "Add remark", description = "Add a remark to a segment")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Remark added successfully",
//                    content = @Content(schema = @Schema(implementation = Resp.class)))
//    })
//    public ResponseEntity<Resp> addRemark(
//            @PathVariable("segmentId") Long segmentId,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request body containing userId and content",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = HashMap.class, requiredProperties = {"userId", "content"}),
//                            examples = @ExampleObject(value = "{\"userId\": 123, \"content\": \"Example remark content\"}")))
//            @RequestBody HashMap<String, String> body) {
//        String content = body.get("content");
//        Long userId = Long.valueOf(body.get("userId"));
//        return ResponseEntity.ok(Resp.ok(remarkService.addRemark(content, userId, segmentId)));
//    }

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * 创建片段
     *
     * @param request 包含请求参数的Map，包括projectId、summary、scope和workflows
     * @return Resp对象表示响应结果
     */
    @PostMapping
    @Operation(summary = "Create Segment", description = "Create a new segment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remark created successfully",
                    content = @Content(schema = @Schema(implementation = Resp.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body containing projectId, summary, scope, and workflows",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class, requiredProperties = {"projectId", "summary", "scope", "workflows"}),
                    examples = @ExampleObject(value = "{\"projectId\": \"1\", \"summary\": \"summary\", \"scope\": \"114:514\", \"workflows\": [\"TIMELINE\", \"K_TIMELINE\", \"S_TIMELINE\", \"TRANSLATION\", \"EFFECT\"]}")
            )
    )
    public Resp createSegment(@RequestBody Map<String, Object> request) {
        // 从请求参数中获取projectId
        Long projectId = Long.valueOf((String) request.get("projectId"));

        // 从请求参数中获取summary和scope
        String summary = (String) request.get("summary");
        String scope = (String) request.get("scope");

//        // 使用正则表达式验证scope格式
//        if (!Pattern.matches("\\d+:\\d+", scope)) {
//            throw new RuntimeException("scope format error");
//        }

        // 从请求参数中获取workflows
        List<String> workflows = (List<String>) request.get("workflows");

        // 创建一个新的Segment对象
        Segment segment = new Segment();
        segment.setSummary(summary);
        segment.setScope(scope);
        segment.setIsGlobal(false);
        segment.setProject(projectRepository.getReferenceById(projectId));
        segmentService.saveSegment(segment);

        // 根据workflows创建相应的Task对象并保存
        taskService.saveTasks(workflows.stream().map(workflow -> {
            Task task = new Task();
            task.setSegment(segment);
            task.setStatus(Task.Status.NOT_ASSIGNED);
            task.setType(Task.Workflow.valueOf(workflow));
            return task;
        }).toList());

        return Resp.ok(segment);
    }

}
