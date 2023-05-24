package com.aguri.captionlive.controller;


import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Resp getAllUsers() {
        return Resp.success(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public Resp getUserById(@PathVariable Long id) {
        return Resp.success(userService.getUserById(id));
    }

    @PostMapping("/")
    public Resp createUser(@RequestBody User user) {
        return Resp.success(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public Resp updateUser(@PathVariable Long id, @RequestBody User user) {
        return Resp.success(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        //actually, we need one column called "deleted" to impl logical delete.
        userService.deleteUser(id);
    }
}

