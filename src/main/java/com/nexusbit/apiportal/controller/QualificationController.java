package com.nexusbit.apiportal.controller;

import com.nexusbit.apiportal.dto.qualification.EducationRequest;
import com.nexusbit.apiportal.dto.qualification.ExpRequest;
import com.nexusbit.apiportal.service.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secure/qualifications")
@RequiredArgsConstructor
public class QualificationController {

    private final QualificationService service;

    @GetMapping("/exp")
    public ResponseEntity<?> getExp(Authentication authentication) {
        return service.getExp(authentication);
    }

    @PostMapping ("/exp")
    public ResponseEntity<?> setExp(Authentication authentication, @RequestBody ExpRequest request) {
        return service.setExp(authentication, request);
    }

    @GetMapping("/education")
    public ResponseEntity<?> getEducation(Authentication authentication) {
        return service.getEducation(authentication);
    }

    @PostMapping("/education")
    public ResponseEntity<?> setEducation(Authentication authentication, @RequestBody EducationRequest request) {
        return service.setEducation(authentication, request);
    }
}



