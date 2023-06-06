package com.aguri.captionlive.controller;

import com.aguri.captionlive.DTO.UserRequest;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Resp> getAllUsers() {
        return ResponseEntity.ok(Resp.ok(userService.getAllUsers()));
    }

    @PostMapping
    public ResponseEntity<Resp> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(Resp.ok(userService.createUser(userRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resp> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(Resp.ok(userService.getUserById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resp> deleteUser(@PathVariable Long id) {
        userService.getUserById(id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resp> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(Resp.ok(userService.updateUser(id, userRequest)));
    }

    @GetMapping("/{userId}/accessibleProjects")
    public ResponseEntity<Resp> getAllAccessibleProjects(@PathVariable Long userId) {
        List<Project> allAccessibleProjects = userService.getAllAccessibleProjects(userId);
        return ResponseEntity.ok(Resp.ok(allAccessibleProjects));
    }

    @GetMapping("/{userId}/committedProjects")
    public ResponseEntity<Resp> getAllCommittedProjects(@PathVariable Long userId) {
        List<Project> allAccessibleProjects = userService.getAllCommittedProjects(userId);
        return ResponseEntity.ok(Resp.ok(allAccessibleProjects));
    }

    @GetMapping("/{userId}/organizations")
    public ResponseEntity<Resp> getMyOrganizations(@PathVariable Long userId) {
        List<Organization> organizations = userService.getUserById(userId).getOrganizations();
        return ResponseEntity.ok(Resp.ok(organizations));
    }

    @PutMapping("/{userId}/description")
    public ResponseEntity<Resp> updateDescription(@RequestBody HashMap<String, String> body, @PathVariable Long userId) {
        String description = body.get("description");
        return ResponseEntity.ok(Resp.ok(userService.updateDescription(userId, description)));
    }
    @PutMapping("/{userId}/avatar")
    public ResponseEntity<Resp> updateAvatar(@RequestBody HashMap<String, String> body, @PathVariable Long userId) {
        Long avatarId = Long.valueOf(body.get("avatarId"));
        return ResponseEntity.ok(Resp.ok(userService.updateAvatar(userId, avatarId)));
    }
}
