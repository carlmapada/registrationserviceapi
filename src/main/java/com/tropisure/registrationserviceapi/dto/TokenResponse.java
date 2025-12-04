package com.tropisure.registrationserviceapi.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Builder
@Schema(description = "Response representing a generated token and its associated event type.")
public class TokenResponse {

    @Schema(
            description = "Unique identifier of the token.",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    private UUID tokenId;

    @Schema(
            description = "Type of event or action associated with this token (e.g., PASSWORD_RESET, EMAIL_VERIFICATION).",
            example = "PASSWORD_RESET"
    )
    private String eventType;
}


