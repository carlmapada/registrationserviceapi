package com.tropisure.registrationserviceapi.repository;


import com.tropisure.registrationserviceapi.entity.AgentInvitationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface AgentInvitationTokenRepository extends JpaRepository<AgentInvitationToken, UUID> {
    Optional<AgentInvitationToken> findByToken(String token);
}


