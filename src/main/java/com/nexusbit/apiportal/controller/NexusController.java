package com.nexusbit.apiportal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexusbit.apiportal.model.nexusModels.RequestModel;
import com.nexusbit.apiportal.model.nexusModels.ResponseModel;
import com.nexusbit.apiportal.processor.PortalRequestProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class NexusController <T>{

    private final PortalRequestProcessor portalRequestProcessor;

    @PostMapping("/secure")
    public ResponseModel secureRequest(Authentication authentication, @RequestBody String data) throws IOException {
        return portalRequestProcessor.processSecureRequest(authentication, data);
    }

    @PostMapping("/public")
    public ResponseModel publicRequest(Authentication authentication, @RequestBody String data) throws JsonProcessingException {
        return portalRequestProcessor.processPublicRequest(authentication, data);
    }

}
