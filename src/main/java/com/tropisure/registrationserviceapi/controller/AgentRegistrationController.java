package com.tropisure.registrationserviceapi.controller;

import com.tropisure.registrationserviceapi.dto.AgentRegistrationRequest;
import com.tropisure.registrationserviceapi.dto.AgentRegistrationResponse;
import com.tropisure.registrationserviceapi.service.AgentRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentRegistrationController {

    private final AgentRegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<AgentRegistrationResponse> register(@RequestBody AgentRegistrationRequest request) {
        return ResponseEntity.ok(registrationService.registerAgent(request));
    }
}
