package com.nexusbit.apiportal.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utils")
@RequiredArgsConstructor
public class UtilsController {

    @GetMapping("/ipCheck")
    public String getIp(HttpServletRequest request){
        String clientIp = request.getRemoteAddr();
        return "{ \"ip\" : \"" + clientIp + "\" }";
    }

}
