package com.tropisure.registrationserviceapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Generic API error response returned when a request fails.")
public class ApiErrResponse {

    @Schema(
            description = "Error message describing what went wrong.",
            example = "Invalid username or password."
    )
    private String message;

    @Schema(
            description = "HTTP status code associated with the error.",
            example = "400"
    )
    private int status;
}

