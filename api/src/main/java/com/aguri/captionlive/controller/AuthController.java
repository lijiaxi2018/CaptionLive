package com.aguri.captionlive.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aguri.captionlive.DTO.SignInRequest;
import com.aguri.captionlive.DTO.SignInResponse;
import com.aguri.captionlive.common.resp.Resp;
import com.aguri.captionlive.model.JwtTokenProvider;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.service.UserService;



@RestController
public class AuthController {

    // @Autowired
    // private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest request) {


        String password = request.getPassword();
        String username = request.getUsername();
        User user = userService.getUserByUsername(username);
        if (!Objects.equals(user.getPassword(), password)) {
            return ResponseEntity.ok(Resp.failed("invalid password"));
        }

        try{
            String token = tokenProvider.generateToken(username);

            SignInResponse data = new SignInResponse();
            
            data.setToken(token);
            return ResponseEntity.ok(Resp.ok(data));

        }catch(Exception e){
            return ResponseEntity.ok(Resp.ok(e));
        }
    }
}

