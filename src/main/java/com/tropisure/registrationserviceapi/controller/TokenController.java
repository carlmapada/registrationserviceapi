package com.tropisure.registrationserviceapi.controller;

import com.tropisure.registrationserviceapi.dto.TokenResponse;
import com.tropisure.registrationserviceapi.entity.AgentInvitationToken;
import com.tropisure.registrationserviceapi.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
@Tag(name = "Token Management", description = "Endpoints for managing and logging token events.")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/{tokenId}/log")
    @Operation(
            summary = "Log a token event",
            description = "Logs the usage of a token for a specific event type and returns the token information."
    )
    public ResponseEntity<TokenResponse> logTokenEvent(
            @Parameter(description = "UUID of the token to log", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable String tokenId,

            @Parameter(description = "Type of event to log for the token", example = "PASSWORD_RESET")
            @RequestParam String eventType
    ) {
        UUID uuid = UUID.fromString(tokenId);

        AgentInvitationToken token = new AgentInvitationToken();
        token.setId(uuid);

        tokenService.logTokenUsage(token, eventType);

        return ResponseEntity.ok(TokenResponse.builder()
                .tokenId(token.getId())
                .eventType(eventType)
                .build());
    }
}




