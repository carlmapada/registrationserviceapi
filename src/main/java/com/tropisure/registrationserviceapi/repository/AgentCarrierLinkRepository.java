package com.tropisure.registrationserviceapi.repository;

import com.tropisure.registrationserviceapi.entity.AgentCarrierLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgentCarrierLinkRepository extends JpaRepository<AgentCarrierLink, UUID> { }

