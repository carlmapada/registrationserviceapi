package com.tropisure.registrationserviceapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request payload for registering a new agent.")
public class AgentRegistrationRequest {

    @Schema(description = "Agent's first name.", example = "John")
    private String firstName;

    @Schema(description = "Agent's last name.", example = "Doe")
    private String lastName;

    @Schema(description = "Agent's email address.", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Agent's phone number including country code.", example = "+1-555-123-4567")
    private String phone;

    @Schema(description = "Agent's country. Either 'US' or 'CA'.", example = "US")
    private String country;

    @Schema(description = "Registration type: SELF for self-registration, INVITED for invitation-based registration.", example = "SELF")
    private String registrationType;

    @Schema(description = "Invitation token for invited registration (optional).", example = "abcd1234", nullable = true)
    private String invitationToken;

    @Schema(description = "Password for the agent account.", example = "P@ssw0rd2025!")
    private String password;
}

