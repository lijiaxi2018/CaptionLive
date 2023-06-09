package com.aguri.captionlive.controller;

import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.DTO.ProjectRequest;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.*;
import com.aguri.captionlive.service.OwnershipService;
import com.aguri.captionlive.service.ProjectService;
import com.aguri.captionlive.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private SegmentService segmentService;

//    @GetMapping
//    public ResponseEntity<Resp> getAllProjects() {
//        List<Project> projects = projectService.getAllProjects();
//        return ResponseEntity.ok(Resp.ok(projects));
//    }

    @GetMapping("/public")
    public ResponseEntity<Resp> getAllPublicProjects() {
        List<ProjectInfo> projects = projectService.getAllPublicProjects();
        return ResponseEntity.ok(Resp.ok(projects));
    }

    @PostMapping
    public ResponseEntity<Resp> createProject(@RequestBody ProjectRequest projectRequest) {
        Project createdProject = projectService.createProject(projectRequest);
        return ResponseEntity.ok(Resp.ok(createdProject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getProjectById(@PathVariable Long id) {
        ProjectInfo projectInfo = ProjectInfo.generateProjectInfo(projectService.getProjectById(id));
        return ResponseEntity.ok(Resp.ok(projectInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resp> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(Resp.ok());
    }

    @PutMapping
    public ResponseEntity<Resp> updateProject(@RequestBody ProjectRequest projectRequest) {
        ProjectInfo updatedProject = ProjectInfo.generateProjectInfo(projectService.updateProject(projectRequest));
        return ResponseEntity.ok(Resp.ok(updatedProject));
    }

    @GetMapping("/{projectIid}/users")
    public ResponseEntity<Resp> getAllAccessibleUsers(@PathVariable Long projectIid) {
        List<User> users = projectService.getAllAccessibleUsers(projectIid);
        return ResponseEntity.ok(Resp.ok(users));
    }

    @GetMapping("/{projectId}/organizations")
    public ResponseEntity<Resp> getAllAccessibleOrganizations(@PathVariable Long projectId) {
        List<Organization> organizations = projectService.getAllAccessibleOrganizations(projectId);
        return ResponseEntity.ok(Resp.ok(organizations));
    }

    @Autowired
    OwnershipService ownershipService;

    @PutMapping("/{projectId}/organizations/{organizationId}")
    public ResponseEntity<Resp> shareProject2Organization(@PathVariable Long organizationId, @PathVariable Long projectId) {
        ownershipService.shareProject2Organization(projectId, organizationId);
        return ResponseEntity.ok(Resp.ok());
    }

    @PutMapping("/{projectId}/users/{userId}")
    public ResponseEntity<Resp> shareProject2User(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.shareProject2User(projectId, userId);
        return ResponseEntity.ok(Resp.ok());
    }

//    @GetMapping("/{projectId}/segments")
//    public ResponseEntity<Resp> getAllSegments(@PathVariable Long projectId) {
//        List<Segment> segments = segmentService.getAllSegments(projectId);
//        return ResponseEntity.ok(Resp.ok(segments));
//    }
//

    @PutMapping("/{projectId}/cover")
    public ResponseEntity<Resp> updateCover(@RequestBody HashMap<String, String> body, @PathVariable Long projectId) {
        Long coverId = Long.valueOf(body.get("coverId"));
        Project updatedProject = projectService.updateCover(projectId, coverId);
        return ResponseEntity.ok(Resp.ok(updatedProject));
    }
}
