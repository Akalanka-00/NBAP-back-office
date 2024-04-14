package com.nexusbit.apiportal.controller;

import com.nexusbit.apiportal.dto.UserRequest;
import com.nexusbit.apiportal.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostConstruct
    public void init(){
        long value = new Date().getTime();
        System.out.println(value);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(HttpServletRequest servletRequest, @RequestBody UserRequest request){
        return userService.registerUser(request);
    }

    @RequestMapping("/login")
    public ResponseEntity<?> loginUser(Authentication authentication, @RequestHeader("authorization") String authorization){
        return userService.loginUser(authentication, authorization);
    }
}
