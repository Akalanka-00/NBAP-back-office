package com.nexusbit.apiportal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexusbit.apiportal.model.nexusModels.Response;
import com.nexusbit.apiportal.processor.RequestProcessor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class NexusController {

    private final RequestProcessor portalRequestProcessor;

    @PostMapping("/secure")
    @ResponseStatus(HttpStatus.CREATED)
    public Response secureRequest(Authentication authentication, HttpServletRequest request, @RequestBody String data) throws IOException {
        return portalRequestProcessor.processSecureRequest(authentication, request, data);
    }

    @PostMapping("/public")
    @ResponseStatus(HttpStatus.CREATED)
    public Response publicRequest(Authentication authentication, @RequestBody String data) throws JsonProcessingException {
        return portalRequestProcessor.processPublicRequest(authentication, data);
    }

}
