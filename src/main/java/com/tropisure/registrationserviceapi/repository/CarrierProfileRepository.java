package com.tropisure.registrationserviceapi.repository;

import com.tropisure.registrationserviceapi.entity.CarrierProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarrierProfileRepository extends JpaRepository<CarrierProfile, UUID> { }

