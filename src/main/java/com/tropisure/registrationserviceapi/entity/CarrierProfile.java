package com.tropisure.registrationserviceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carrier_profile", schema = "registration_db")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrierProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String naicCode;

    private String contactEmail;

    @Column(length = 2)
    private String country; // US or CA

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "carrier")
    private List<AgentCarrierLink> agentLinks;

    // Getters and setters
}
