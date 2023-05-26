package com.aguri.captionlive.controller;


import com.aguri.captionlive.DTO.SignInRequest;
import com.aguri.captionlive.DTO.SignInResponse;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.FileRecord;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.service.FileRecordService;
import com.aguri.captionlive.service.ProjectService;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    private FileRecordService fileRecordService;


    @GetMapping
    public Resp getAllUsers() {
        return Resp.success(userService.getAllUsers());
    }

    @PostMapping
    public Resp createUser(@RequestBody User user) {
        return Resp.success(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public Resp getUserById(@PathVariable Long id) {
        return Resp.success(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public Resp deleteUser(@PathVariable Long id) {
        userService.getUserById(id);
        userService.deleteUser(id);
        return Resp.success();
    }

    @PutMapping("/{id}")
    public Resp updateUser(@PathVariable Long id, @RequestBody User user) {
        return Resp.success(userService.updateUser(id, user));
    }

    @PostMapping("/signIn")
    public Resp signIn(@RequestBody SignInRequest signInRequest) {
        String password = signInRequest.getPassword();
        String username = signInRequest.getUsername();
        User user = userService.getUserByUsername(username);
        if (!Objects.equals(user.getPassword(), password)) {
            return Resp.failed("error password");
        }
        SignInResponse data = new SignInResponse();
        // TODO
        data.setToken("this is a token");
        return Resp.success(data);
    }

    @PostMapping("/uploadAvatar/{id}")
    public Resp uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile file) {
        User user = userService.saveAvatarToStorage(id, file);
        return Resp.success(user);
    }


}