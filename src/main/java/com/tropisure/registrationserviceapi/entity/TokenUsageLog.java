package com.tropisure.registrationserviceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "token_usage_log", schema = "registration_db")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenUsageLog {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "token_id", nullable = false)
    private AgentInvitationToken token;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private AgentProfile agent;

    @Column(nullable = false)
    private String eventType; // VIEWED, USED, EXPIRED

    private LocalDateTime eventTime;

    // Getters and setters
}

