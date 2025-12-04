package com.tropisure.registrationserviceapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "Response returned after successful agent registration.")
public class AgentRegistrationResponse {

    @Schema(
            description = "Unique identifier of the registered agent.",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    private UUID agentId;

    @Schema(
            description = "Email of the registered agent.",
            example = "john.doe@example.com"
    )
    private String email;

    @Schema(
            description = "Current status of the agent registration (e.g., PENDING, ACTIVE).",
            example = "PENDING"
    )
    private String status;
}


