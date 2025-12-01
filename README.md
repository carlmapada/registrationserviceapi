# Tropisure RegistrationService API

## Overview
The Tropisure Registration API is a Spring Boot microservice responsible for managing agent registrations for Tropisure. It supports both self-registration and invitation-based registration and integrates with AWS Cognito for authentication and role management.

This service handles:
- Agent profile creation
- Token validation for invitation-based registrations
- Linking agents to MGAs or Carriers
- Cognito user creation and role assignment

## Tech Stack
- **Backend**: Java, Spring Boot, Spring Data JPA
- **Database**: PostgreSQL
- **Authentication**: AWS Cognito (User Pools)
- **API Testing**: Postman
- **Build Tool**: Maven

## Project Structure
```
tropisure-registration-service/
│
├─ src/main/java/com/tropisure/registration/
│   ├─ entity/
│   │   ├─ AgentProfile.java
│   │   ├─ MGAProfile.java
│   │   ├─ CarrierProfile.java
│   │   ├─ AgentInvitationToken.java
│   │   ├─ TokenUsageLog.java
│   │   ├─ AgentMGALink.java
│   │   └─ AgentCarrierLink.java
│   ├─ repository/
│   │   ├─ AgentProfileRepository.java
│   │   ├─ MGAProfileRepository.java
│   │   ├─ CarrierProfileRepository.java
│   │   ├─ AgentInvitationTokenRepository.java
│   │   ├─ TokenUsageLogRepository.java
│   │   ├─ AgentMGALinkRepository.java
│   │   └─ AgentCarrierLinkRepository.java
│   ├─ service/
│   │   ├─ AgentRegistrationService.java
│   │   ├─ TokenService.java
│   │   └─ CognitoClientService.java
│   ├─ controller/
│   │   ├─ AgentRegistrationController.java
│   │   └─ TokenController.java
│   └─ dto/
│       ├─ AgentRegistrationRequest.java
│       ├─ AgentRegistrationResponse.java
│       └─ TokenResponse.java
└─ resources/
    ├─ application.yml
    └─ logback-spring.xml
```

## Database
### Schema: registration
Tables used:
- `agent_profile`
- `mga_profile`
- `carrier_profile`
- `agent_invitation_token`
- `token_usage_log`
- `agent_mga_link`
- `agent_carrier_link`

**Note:** These tables are already created in `registration_db`.

### Example Table: agent_profile
```sql
CREATE TABLE registration.agent_profile (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(150) NOT NULL,
    last_name VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50),
    country VARCHAR(2) NOT NULL CHECK (country IN ('US','CA')),
    date_of_birth DATE,
    tax_id_last4 VARCHAR(4),
    tax_id_hash VARCHAR(200),
    registration_type VARCHAR(20) NOT NULL CHECK (registration_type IN ('SELF','INVITED')),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING','ACTIVE','SUSPENDED')),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP NULL
);
```

## API Endpoints
### Agent Registration
- **POST** `/api/agents/register`
  - Registers a new agent (self or invited)
  - Request Body: `AgentRegistrationRequest` (firstName, lastName, email, phone, country, registrationType, optional token)
  - Response: `AgentRegistrationResponse` (agentId, email, status)

### Token Event Logging
- **POST** `/api/agents/{tokenId}/log`
  - Logs token usage events (VIEWED, USED, EXPIRED)

## Cognito Integration
- AWS Cognito User Pool handles authentication.
- Users are created in Cognito upon registration.
- Agent role is assigned as `AGENT`.
- Tokens are verified before creating links to MGA or Carrier.

## Running Locally
1. Set up PostgreSQL and create `tropisure` database.
2. Update `application.yml` with database credentials.
3. Use the local `MockCognitoClientService` for testing without AWS.
4. Build and run:
```bash
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Testing
- Use Postman to test registration endpoints.
- Include scenarios:
  - Self-registration
  - Invitation-based registration
  - Invalid token handling

## Notes
- Password is set during registration.
- Token validation happens before persisting the agent profile.
- Agents can be linked to multiple MGAs or Carriers based on invitation.
- Global exception handler is included for consistent error responses.

