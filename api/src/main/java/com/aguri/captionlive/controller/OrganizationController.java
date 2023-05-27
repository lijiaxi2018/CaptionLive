package com.aguri.captionlive.controller;


import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.service.OrganizationService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Resp> getAllOrganizations() {
        List<Organization> organizations = organizationService.getAllOrganizations();
        return ResponseEntity.ok(Resp.ok(organizations));
    }

    @PostMapping
    public ResponseEntity<Resp> createOrganization(@RequestBody Organization organization) {
        Organization createdOrganization = organizationService.createOrganization(organization);
        return ResponseEntity.status(HttpStatus.CREATED).body(Resp.ok(createdOrganization));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getOrganizationById(@PathVariable Long id) {
        Organization organization = organizationService.getOrganizationById(id);
        return ResponseEntity.ok(Resp.ok(organization));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resp> updateOrganization(@PathVariable Long id, @RequestBody Organization organization) {
        Organization updatedOrganization = organizationService.updateOrganization(id, organization);
        return ResponseEntity.ok(Resp.ok(updatedOrganization));
    }

    @GetMapping("/getAllUsers/{organizationId}")
    public ResponseEntity<Resp> getAllUsersByOrganizationId(@PathVariable Long organizationId) {
        organizationService.getOrganizationById(organizationId);
        List<User> users = userService.getUsersByOrganizationId(organizationId);
        return ResponseEntity.ok(Resp.ok(users));
    }

    @PostMapping("/uploadAvatar/{id}")
    public ResponseEntity<Resp> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile file) {
        Organization organization = organizationService.saveAvatarToStorage(id, file);
        return ResponseEntity.ok(Resp.ok(organization));
    }

    @GetMapping("/getAllProjects/{organizationId}")
    public ResponseEntity<Resp> getAllProjects(@PathVariable Long organizationId) {
        List<Project> projects = organizationService.getAllProjects(organizationId);
        return ResponseEntity.ok(Resp.ok(projects));
    }

}
