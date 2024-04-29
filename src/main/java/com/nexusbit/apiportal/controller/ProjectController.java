package com.nexusbit.apiportal.controller;

import com.nexusbit.apiportal.dto.project.ProjectRequest;
import com.nexusbit.apiportal.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/secure/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;
    @GetMapping
    public ResponseEntity<?> getProjects(Authentication authentication) {
        return service.retrieveProjects(authentication);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newProject(Authentication authentication, @RequestBody ProjectRequest request){
        return service.newProject(authentication, request);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewProject(Authentication authentication, @PathVariable String id){
        return service.retrieveProject(id, authentication);
    }
}
