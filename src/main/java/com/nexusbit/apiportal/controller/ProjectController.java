package com.nexusbit.apiportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure/projects")
@RequiredArgsConstructor
public class ProjectController {

    @GetMapping
    public ResponseEntity<?> getProjects(Authentication authentication) {
        return ResponseEntity.status(200).body(authentication);
    }
}
