package com.aguri.captionlive.controller;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import com.aguri.captionlive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aguri.captionlive.DTO.UserRequest;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.common.util.JwtTokenProvider;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.service.UserService;
import com.google.gson.JsonObject;


@RestController
public class AuthController {

    // @Autowired
    // private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("api/login")
    public ResponseEntity<?> authenticateUser(@RequestBody HashMap<String, String> body) {


        String password = body.get("password");
        String username = body.get("username");
        User user = userService.getUserByUsername(username);
        if (!Objects.equals(user.getPassword(), password)) {
            return ResponseEntity.ok(Resp.failed("invalid password"));
        }

        try {

            JsonObject loginResponse = new JsonObject();
            JsonObject data = new JsonObject();

            String token = tokenProvider.generateToken(username);

            data.addProperty("id", user.getUserId());
            data.addProperty("token", token);

            loginResponse.addProperty("message", "success");
            loginResponse.add("token", data);

            return ResponseEntity.ok(loginResponse.toString());

        } catch (Exception e) {
            return ResponseEntity.ok(Resp.ok(e));
        }
    }

    @PostMapping("api/signUp")
    public ResponseEntity<?> registerUser(@RequestBody HashMap<String, String> body) {


        String password = body.get("password");
        String username = body.get("username");

        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(Resp.failed("User already exists"));
        }

        String email = body.get("email");
        String qq = body.get("qq");
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setEmail(email);
        userRequest.setQq(qq);
        userService.createUser(userRequest);

        User user = userService.getUserByUsername(username);


        try {
            JsonObject loginResponse = new JsonObject();
            JsonObject data = new JsonObject();

            String token = tokenProvider.generateToken(username);

            data.addProperty("id", user.getUserId());
            data.addProperty("token", token);

            loginResponse.addProperty("message", "success");
            loginResponse.add("token", data);

            return ResponseEntity.ok(loginResponse.toString());

        } catch (Exception e) {
            return ResponseEntity.ok(Resp.ok(e));
        }
    }

}

