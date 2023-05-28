package com.aguri.captionlive.controller;

import com.aguri.captionlive.DTO.SignInRequest;
import com.aguri.captionlive.DTO.SignInResponse;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.model.Project;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.repository.FileRecordRepository;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    FileRecordService fileRecordService;

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

    @PostMapping("/signIn")
    public ResponseEntity<Resp> signIn(@RequestBody SignInRequest signInRequest) {
        String password = signInRequest.getPassword();
        String username = signInRequest.getUsername();
        User user = userService.getUserByUsername(username);
        if (!Objects.equals(user.getPassword(), password)) {
            return ResponseEntity.ok(Resp.failed("invalid password"));
        }
        SignInResponse data = new SignInResponse();
        // TODO
        data.setToken("this is a token");
        return ResponseEntity.ok(Resp.ok(data));
    }

    @PostMapping("/uploadAvatar/{id}")
    public ResponseEntity<Resp> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile file) {
        User user = userService.uploadAvatar(id, file);
        return ResponseEntity.ok(Resp.ok(user));
    }

    @GetMapping("/getAllAccessibleProjects/{id}")
    public ResponseEntity<Resp> getAllAccessibleProjects(@PathVariable Long id) {
        List<Project> allAccessibleProjects = userService.getAllAccessibleProjects(id);
        return ResponseEntity.ok(Resp.ok(allAccessibleProjects));
    }

    @GetMapping("/getAllCommittedProjects/{id}")
    public ResponseEntity<Resp> getAllCommittedProjects(@PathVariable Long id) {
        List<Project> allAccessibleProjects = userService.getAllCommittedProjects(id);
        return ResponseEntity.ok(Resp.ok(allAccessibleProjects));
    }

    @GetMapping("/downloadAvatar/{userId}")
    public ResponseEntity<Resource> downloadAvatar(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        FileRecord fileRecord = fileRecordService.getFileRecordById(user.getAvatar());
        return fileRecordService.download(fileRecord);
    }
}
