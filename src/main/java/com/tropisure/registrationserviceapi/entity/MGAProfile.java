package com.tropisure.registrationserviceapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "registration_db", name = "mga_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MGAProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String taxId;
    private String address;
    private String contactName;

    @Column(nullable = false)
    private String contactEmail;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "mga")
    private List<AgentMGALink> agentLinks;

}

