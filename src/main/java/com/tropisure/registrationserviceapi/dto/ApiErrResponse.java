package com.tropisure.registrationserviceapi.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrResponse {
    private String message;
    private int status;
}
