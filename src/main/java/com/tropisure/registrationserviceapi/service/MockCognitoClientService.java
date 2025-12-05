package com.tropisure.registrationserviceapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@Profile("local")  // Only active in local profile
public class MockCognitoClientService implements ICognitoClientService {

    // Store users and group assignments in memory
    private final Map<String, MockUser> users = new HashMap<>();

    @Override
    public AdminCreateUserResponse createUser(String username, String email) {
        // Simulate successful Cognito user creation
        System.out.println("[MOCK] Cognito user created: " + email);
        return AdminCreateUserResponse.builder().build();
    }

    @Override
    public AdminInitiateAuthResponse authenticate(String userPoolId, String clientId, String username, String password) {
        System.out.println("[MOCK] Cognito authentication success for: " + username);
        return AdminInitiateAuthResponse.builder().authenticationResult(
                b -> b.accessToken("MOCK_ACCESS_TOKEN_" + UUID.randomUUID())
        ).build();
    }

    @Override
    public String createUserWithRole(String username, String email, String password, String role) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("User already exists in mock Cognito: " + username);
        }

        MockUser user = new MockUser(username, email, password, role);
        users.put(username, user);
        System.out.println("Created mock Cognito user: " + username + " with role " + role);
        return username;
    }

    // Simple inner class for mock user
    private static class MockUser {
        String username;
        String email;
        String password;
        String role;

        public MockUser(String username, String email, String password, String role) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.role = role;
        }
    }
}

