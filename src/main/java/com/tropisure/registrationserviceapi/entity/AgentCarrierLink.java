package com.tropisure.registrationserviceapi.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "agent_carrier_link", schema = "registration_db")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentCarrierLink {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agent_id", nullable = false)
    private AgentProfile agent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    private CarrierProfile carrier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id")
    private AgentInvitationToken token;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_status", nullable = false)
    private RelationshipStatus relationshipStatus; // PENDING, ACTIVE, INACTIVE

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.relationshipStatus == null) {
            this.relationshipStatus = RelationshipStatus.PENDING;
        }
    }
}


