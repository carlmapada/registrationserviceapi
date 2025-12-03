package com.tropisure.registrationserviceapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TokenResponse {
    private UUID tokenId;
    private String eventType;
}

