package com.aguri.captionlive.controller;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aguri.captionlive.DTO.SignInRequest;
import com.aguri.captionlive.DTO.SignInResponse;
import com.aguri.captionlive.DTO.UserCreateRequest;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.common.JwtTokenProvider;
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

    @PostMapping("api/login")
    public ResponseEntity<?> authenticateUser(@RequestBody HashMap<String, String> body) {


        String password = body.get("password");
        String username = body.get("username");
        User user = userService.getUserByUsername(username);
        if (!Objects.equals(user.getPassword(), password)) {
            return ResponseEntity.ok(Resp.failed("invalid password"));
        }

        try{

            JsonObject loginResponse = new JsonObject();
            JsonObject data = new JsonObject();

            String token = tokenProvider.generateToken(username);

            data.addProperty("id", user.getUserId());
            data.addProperty("token", token);

            loginResponse.addProperty("message", "success");
            loginResponse.add("token",data);
            
            return ResponseEntity.ok(loginResponse.toString());

        }catch(Exception e){
            return ResponseEntity.ok(Resp.ok(e));
        }
    }

    @PostMapping("api/signUp")
    public ResponseEntity<?> registerUser(@RequestBody HashMap<String, String> body) {


        String password = body.get("password");
        String username = body.get("username");
        String email = body.get("email");
        String qq = body.get("qq");
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setUsername(username);
        userCreateRequest.setPassword(password);
        userCreateRequest.setEmail(email);
        userCreateRequest.setQq(qq);
        userService.createUser(userCreateRequest);

        User user = userService.getUserByUsername(username);

        
        try{
            JsonObject loginResponse = new JsonObject();
            JsonObject data = new JsonObject();

            String token = tokenProvider.generateToken(username);

            data.addProperty("id", user.getUserId());
            data.addProperty("token", token);

            loginResponse.addProperty("message", "success");
            loginResponse.add("token",data);
            
            return ResponseEntity.ok(loginResponse.toString());

        }catch(Exception e){
            return ResponseEntity.ok(Resp.ok(e));
        }
    }
}

