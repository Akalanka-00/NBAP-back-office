package com.nexusbit.apiportal.controller;

import com.nexusbit.apiportal.dto.project.ProjectRequest;
import com.nexusbit.apiportal.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secure/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    @GetMapping("/all")
    public ResponseEntity<?> getProjects(Authentication authentication) {
        return ResponseEntity.status(200).body(authentication);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newProject(Authentication authentication, @RequestBody ProjectRequest request) {
        return projectService.newProject(authentication, request);
    }
}
