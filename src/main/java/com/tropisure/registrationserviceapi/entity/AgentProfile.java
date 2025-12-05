package com.tropisure.registrationserviceapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "registration_db", name = "agent_profile")
public class AgentProfile {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String country; // "US" or "CA"

    private LocalDate dateOfBirth;

    @Column(length = 4)
    private String taxIdLast4;

    private String taxIdHash;

    @Column(nullable = false)
    private String registrationType; // SELF or INVITED

    @Column(nullable = false)
    private String status; // PENDING, ACTIVE, SUSPENDED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "agent")
    private List<AgentInvitationToken> invitationTokens;

    @OneToMany(mappedBy = "agent")
    private List<AgentMGALink> mgaLinks;

    @OneToMany(mappedBy = "agent")
    private List<AgentCarrierLink> carrierLinks;

    @Column(name = "cognito_sub", unique = true)
    private String cognitoSub;
}

