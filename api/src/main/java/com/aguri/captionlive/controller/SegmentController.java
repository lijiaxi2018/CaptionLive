package com.aguri.captionlive.controller;

import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Segment;
import com.aguri.captionlive.model.Task;
import com.aguri.captionlive.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/segments")
public class SegmentController {

//    @Autowired
//    private SegmentService segmentService;
//
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
//    @DeleteMapping("/{segmentId}")
//    public ResponseEntity<Resp> deleteSegment(@PathVariable Long segmentId) {
//        segmentService.deleteSegment(segmentId);
//        return ResponseEntity.noContent().build();
//    }
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
}
