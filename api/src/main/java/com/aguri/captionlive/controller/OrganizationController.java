package com.aguri.captionlive.controller;


import com.aguri.captionlive.DTO.OrganizationRequest;
import com.aguri.captionlive.DTO.ProjectInfo;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.service.OrganizationService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Resp> createOrganization(@RequestBody OrganizationRequest organization) {
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
    public ResponseEntity<Resp> updateOrganization(@PathVariable Long id, @RequestBody OrganizationRequest organization) {
        Organization updatedOrganization = organizationService.updateOrganization(id, organization);
        return ResponseEntity.ok(Resp.ok(updatedOrganization));
    }

    @GetMapping("{organizationId}/users")
    public ResponseEntity<Resp> getAllUsersByOrganizationId(@PathVariable Long organizationId) {
        organizationService.getOrganizationById(organizationId);
        List<User> users = userService.getUsersByOrganizationId(organizationId);
        return ResponseEntity.ok(Resp.ok(users));
    }

    @GetMapping("/{organizationId}/projects")
    public ResponseEntity<Resp> getPagedProjects(
            @PathVariable Long organizationId,
            @RequestParam(value = "searchTxt", defaultValue = "") String searchTxt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created_time") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        List<ProjectInfo> projectsInfo = organizationService.getPagedProjects(organizationId, searchTxt, page, size, sortBy, sortOrder);
        return ResponseEntity.ok(Resp.ok(projectsInfo));
    }

}
