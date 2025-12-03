package com.tropisure.registrationserviceapi.controller;


import com.tropisure.registrationserviceapi.dto.TokenResponse;
import com.tropisure.registrationserviceapi.entity.AgentInvitationToken;
import com.tropisure.registrationserviceapi.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/{tokenId}/log")
    public ResponseEntity<TokenResponse> logTokenEvent(@PathVariable String tokenId, @RequestParam String eventType) {
        AgentInvitationToken token = new AgentInvitationToken();
        token.setId(java.util.UUID.fromString(tokenId));
        tokenService.logTokenUsage(token, eventType);

        return ResponseEntity.ok(TokenResponse.builder()
                .tokenId(token.getId())
                .eventType(eventType)
                .build());
    }
}


