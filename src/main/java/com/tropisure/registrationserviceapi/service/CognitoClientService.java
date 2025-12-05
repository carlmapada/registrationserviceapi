package com.tropisure.registrationserviceapi.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("prod")
public class CognitoClientService implements ICognitoClientService{

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    @Value("${aws.cognito.app-client-id}")
    private String appClientId;

    private static final Logger log = LoggerFactory.getLogger(CognitoClientService.class);

    @Override
    public AdminCreateUserResponse createUser(String username, String email) {
        AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                .userPoolId(userPoolId)
                .username(username)
                .userAttributes(AttributeType.builder().name("email").value(email).build())
                .messageAction(MessageActionType.SUPPRESS)
                .build();

        return cognitoClient.adminCreateUser(request);
    }

    @Override
    public AdminInitiateAuthResponse authenticate(String userPoolId, String clientId, String username, String password) {
        AdminInitiateAuthRequest request = AdminInitiateAuthRequest.builder()
                .userPoolId(userPoolId)
                .clientId(clientId)
                .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .authParameters(java.util.Map.of(
                        "USERNAME", username,
                        "PASSWORD", password
                ))
                .build();

        return cognitoClient.adminInitiateAuth(request);
    }

    @Override
    public String createUserWithRole(String username, String email, String password, String role) {

        try {
            // 1️⃣ Check if user already exists
            try {
                AdminGetUserResponse existingUser = cognitoClient.adminGetUser(
                        builder -> builder.userPoolId(userPoolId).username(username)
                );
                log.warn("User {} already exists in Cognito pool {}", username, userPoolId);
                return existingUser.username(); // return the existing username (sub not available here)
            } catch (UserNotFoundException ignored) {
                // proceed
            }

            // 2️⃣ Create user
            AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .userAttributes(
                            AttributeType.builder().name("email").value(email).build(),
                            AttributeType.builder().name("email_verified").value("true").build()
                    )
                    .temporaryPassword(password)
                    .messageAction(MessageActionType.SUPPRESS)
                    .build();

            AdminCreateUserResponse createUserResponse = cognitoClient.adminCreateUser(createUserRequest);
            String cognitoSub = createUserResponse.user().username(); // This is the sub
            log.info("Created user {} in Cognito pool {} with sub {}", username, userPoolId, cognitoSub);

//            cognitoClient.adminCreateUser(createUserRequest);
//            log.info("Created user {} in Cognito pool {}", username, userPoolId);

            // 3️⃣ Set permanent password
            AdminSetUserPasswordRequest setPasswordRequest = AdminSetUserPasswordRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .password(password)
                    .permanent(true)
                    .build();

            cognitoClient.adminSetUserPassword(setPasswordRequest);
            log.info("Set permanent password for user {}", username);

            // 4️⃣ Ensure group exists
            try {
                cognitoClient.getGroup(builder -> builder.userPoolId(userPoolId).groupName(role));
            } catch (ResourceNotFoundException e) {
                // Create group if not exist
                cognitoClient.createGroup(CreateGroupRequest.builder()
                        .groupName(role)
                        .userPoolId(userPoolId)
                        .description("Auto-created group for role: " + role)
                        .build());
                log.info("Created Cognito group {}", role);
            }

            // 5️⃣ Add user to group
            AdminAddUserToGroupRequest addToGroupRequest = AdminAddUserToGroupRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .groupName(role)
                    .build();

            cognitoClient.adminAddUserToGroup(addToGroupRequest);
            log.info("Added user {} to group {}", username, role);

            return cognitoSub;

        } catch (CognitoIdentityProviderException e) {
            log.error("Failed to create user {}: {}", username, e.awsErrorDetails().errorMessage(), e);
            throw e;
        }
    }


}
