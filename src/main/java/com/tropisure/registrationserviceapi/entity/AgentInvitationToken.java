package com.tropisure.registrationserviceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "agent_invitation_token", schema = "registration_db")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentInvitationToken {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String agentEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssuerType issuedByType; // MGA or CARRIER

    @Column(name = "issued_by_id", nullable = false)
    private UUID issuedById;  // No @ManyToOne

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private AgentProfile agent;

    @Column(nullable = false, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RelationshipStatus status;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
