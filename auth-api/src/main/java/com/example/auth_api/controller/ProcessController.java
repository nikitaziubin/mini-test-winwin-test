package com.example.auth_api.controller;

import com.example.auth_api.transferobject.ProcessRequest;
import com.example.auth_api.transferobject.ProcessResponse;
import com.example.auth_api.service.AuthService;
import com.example.auth_api.service.ProcessService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProcessController {

    private final AuthService authService;
    private final ProcessService processService;

    public ProcessController(AuthService authService, ProcessService processService) {
        this.authService = authService;
        this.processService = processService;
    }

    @PostMapping("/process")
    public ProcessResponse process(Authentication authentication,
                                   @Valid @RequestBody ProcessRequest request) {
        String email = authentication.getName();
        UUID userId = authService.userIdByEmail(email);
        String result = processService.process(userId, request.text());
        return new ProcessResponse(result);
    }
}

