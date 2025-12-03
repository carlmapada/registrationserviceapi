package com.tropisure.registrationserviceapi.service;

import com.tropisure.registrationserviceapi.dto.AgentRegistrationRequest;
import com.tropisure.registrationserviceapi.dto.AgentRegistrationResponse;
import com.tropisure.registrationserviceapi.entity.*;
import com.tropisure.registrationserviceapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AgentRegistrationService {

    private final AgentProfileRepository agentProfileRepository;
    private final AgentInvitationTokenRepository tokenRepository;
    private final AgentMGALinkRepository agentMGALinkRepository;
    private final AgentCarrierLinkRepository agentCarrierLinkRepository;
    private final ICognitoClientService cognitoClientService;
    private final MGAProfileRepository mgaProfileRepository;
    private final CarrierProfileRepository carrierProfileRepository;


    @Transactional
    public AgentRegistrationResponse registerAgent(AgentRegistrationRequest request) {

        // 1️⃣ Check if agent already exists
        if (agentProfileRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Agent already registered");
        }

        AgentInvitationToken token = null;
        // 2️⃣ Verify token first if provided
        if (request.getInvitationToken() != null && !request.getInvitationToken().isBlank()) {
            token = tokenRepository.findByToken(request.getInvitationToken())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid invitation token"));

            if (token.getStatus() != RelationshipStatus.PENDING) {
                throw new IllegalArgumentException("Invitation token is not valid or already used");
            }
        }

        // 3️⃣ Persist AgentProfile
        AgentProfile agent = AgentProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .country(request.getCountry())
                .registrationType(request.getRegistrationType())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        agent = agentProfileRepository.save(agent);

        // 4️⃣ Handle token linking if token was provided
        if (token != null) {
            token.setAgent(agent);
            token.setStatus(RelationshipStatus.USED);
            tokenRepository.save(token);

            if (token.getIssuedByType() == IssuerType.MGA) {
                MGAProfile mgaProfile = mgaProfileRepository.findById(token.getIssuedById())
                        .orElseThrow(() -> new IllegalArgumentException("MGA not found"));

                AgentMGALink mgaLink = AgentMGALink.builder()
                        .agent(agent)
                        .mga(mgaProfile)
                        .token(token)
                        .build(); // relationshipStatus defaults to PENDING
                agentMGALinkRepository.save(mgaLink);

            } else if (token.getIssuedByType() == IssuerType.CARRIER) {
                CarrierProfile carrierProfile = carrierProfileRepository.findById(token.getIssuedById())
                        .orElseThrow(() -> new IllegalArgumentException("Carrier not found"));

                AgentCarrierLink carrierLink = AgentCarrierLink.builder()
                        .agent(agent)
                        .carrier(carrierProfile)
                        .token(token)
                        .build(); // relationshipStatus defaults to PENDING
                agentCarrierLinkRepository.save(carrierLink);
            }
        }

        // 5️⃣ Create Cognito user
        String username = request.getEmail().split("@")[0];
        cognitoClientService.createUserWithRole(
                //request.getEmail(),
                username,
                request.getEmail(),
                request.getPassword(), // password provided by agent
                "AGENT"
        );
        return AgentRegistrationResponse.builder()
                .agentId(agent.getId())
                .status(agent.getStatus())
                .email(agent.getEmail())
                .build();
    }
}
