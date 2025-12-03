package com.tropisure.registrationserviceapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AgentRegistrationResponse {
    private UUID agentId;
    private String email;
    private String status;
}

