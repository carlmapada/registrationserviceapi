package com.tropisure.registrationserviceapi.dto;

import lombok.Data;

@Data
public class AgentRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country; // US or CA
    private String registrationType; // SELF or INVITED
    private String invitationToken; // optional
    private String password;
}

