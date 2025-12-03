package com.tropisure.registrationserviceapi.service;

import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;

public interface ICognitoClientService {

    AdminCreateUserResponse createUser(String username, String email);
    AdminInitiateAuthResponse authenticate(String userPoolId, String clientId, String username, String password);
    void createUserWithRole(String username, String email, String password, String role);

}
