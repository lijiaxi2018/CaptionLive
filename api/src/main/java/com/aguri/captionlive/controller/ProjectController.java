package com.aguri.captionlive.controller;

import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.ProjectService;
import com.aguri.captionlive.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private FileRecordService fileRecordService;

    @GetMapping
    public ResponseEntity<Resp> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(Resp.ok(projects));
    }

    @GetMapping("/publicProjects")
    public ResponseEntity<Resp> getAllPublicProjects() {
        List<Project> projects = projectService.getAllPublicProjects();
        return ResponseEntity.ok(Resp.ok(projects));
    }

    @PostMapping
    public ResponseEntity<Resp> createProject(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.ok(Resp.ok(createdProject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(Resp.ok(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resp> updateProject(@PathVariable Long id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        return ResponseEntity.ok(Resp.ok(updatedProject));
    }

    @GetMapping("/{projectIid}/users")
    public ResponseEntity<Resp> getAllUsers(@PathVariable Long projectIid) {
        List<User> users = projectService.getAllAccessibleUsers(projectIid);
        return ResponseEntity.ok(Resp.ok(users));
    }

    @GetMapping("/{projectIid}/organizations")
    public ResponseEntity<Resp> getAllOrganizations(@PathVariable Long projectIid) {
        List<Organization> organizations = projectService.getAllAccessibleOrganizations(projectIid);
        return ResponseEntity.ok(Resp.ok(organizations));
    }

    @GetMapping("/{projectId}/segments/")
    public ResponseEntity<Resp> getAllSegments(@PathVariable Long projectId) {
        List<Segment> segments = segmentService.getAllSegments(projectId);
        return ResponseEntity.ok(Resp.ok(segments));
    }

    @GetMapping("/{projectId}/avatar")
    public ResponseEntity<Resource> downloadAvatar(@PathVariable Long projectId) {
        return fileRecordService.download(projectService.getProjectById(projectId).getCoverFileRecord());
    }

    @PostMapping("/{projectId}/avatar")
    public ResponseEntity<Resp> uploadAvatar(@PathVariable Long projectId, @RequestParam MultipartFile file) {
        Project project = projectService.uploadAvatar(projectId, file);
        return ResponseEntity.ok(Resp.ok(project));
    }

}
