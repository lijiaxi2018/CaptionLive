package com.aguri.captionlive.controller;

import com.aguri.captionlive.DTO.UserInfoResponse;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.Organization;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Resp> createUser(@RequestBody User user) {
        return ResponseEntity.ok(Resp.ok(userService.createUser(user)));
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
    public ResponseEntity<Resp> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(Resp.ok(userService.updateUser(id, user)));
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

    @GetMapping("/{userId}/info")
    public ResponseEntity<Resp> info(@PathVariable Long userId) {
        UserInfoResponse userInfoResponse = userService.getUserInfoById(userId);
        return ResponseEntity.ok(Resp.ok(userInfoResponse));
    }

    @GetMapping("/{userId}/organizations")
    public ResponseEntity<Resp> getMyOrganizations(@PathVariable Long userId) {
        List<Organization> organizations = userService.getUserById(userId).getOrganizations();
        return ResponseEntity.ok(Resp.ok(organizations));
    }
}
